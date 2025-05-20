package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.ProductDTO;
import kr.co.lotteOn.dto.ProductNoticeDTO;
import kr.co.lotteOn.dto.ProductOptionDTO;
import kr.co.lotteOn.dto.review.ReviewSummary;
import kr.co.lotteOn.entity.*;
import kr.co.lotteOn.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductNoticeRepository productNoticeRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final PointRepository pointRepository;
    private final OrderItemRepository orderItemRepository;
    private final ReviewRepository reviewRepository;

    private Comparator<Product> getComparator(String sort) {
        return switch (sort) {
            case "low" -> Comparator.comparing(this::getDiscountedPrice);
            case "high" -> Comparator.comparing(this::getDiscountedPrice).reversed();
            case "review" -> Comparator.comparingInt((Product p) ->
                    reviewRepository.countByProductCode(p.getProductCode())).reversed();
            case "rating" -> Comparator.comparingDouble((Product p) ->
                    reviewRepository.getAverageRatingByProductCode(p.getProductCode())).reversed();
            default -> Comparator.comparing(Product::getId).reversed();
        };
    }

    private int getDiscountedPrice(Product p) {
        return p.getPrice() - (p.getPrice() * p.getDiscount() / 100);
    }

    public ProductDTO mapToDTOWithReviewData(Product product, Map<String, ReviewSummary> reviewMap) {
        ProductDTO dto = ProductDTO.fromEntity(product);
        ReviewSummary review = reviewMap.get(product.getProductCode());
        log.warn("⭐ productCode = {}, reviewMap hit = {}", product.getProductCode(), review);
        if (review != null) {
            dto.setAvgRating(review.getAvgRating());
            dto.setViews(review.getReviewCount().intValue());
        } else {
            dto.setAvgRating(0.0);
            dto.setViews(0);
        }
        return dto;
    }

    @Transactional
    public Product saveProduct(ProductDTO dto) {
        // 1. DTO → Entity 매핑
        Product product = modelMapper.map(dto, Product.class);
        product.setViews(0);
        product.setImageList(dto.getImageListFile().getOriginalFilename());
        product.setImageMain(dto.getImageMainFile().getOriginalFilename());
        product.setImageDetail(dto.getImageDetailFile().getOriginalFilename());
        product.setImageInfo(dto.getImageInfoFile().getOriginalFilename());

        // 2. 카테고리 설정
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("카테고리 없음"));
        product.setCategory(category);

        // 3. ID 확보를 위해 먼저 저장
        product = productRepository.saveAndFlush(product);

        // 4. productCode 설정 후 저장
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        product.setProductCode(String.format("P%s%04d", today, product.getId()));

        // 5. 공지사항 연결
        if (dto.getNotice() != null) {
            ProductNotice notice = modelMapper.map(dto.getNotice(), ProductNotice.class);
            product.setNotice(notice); // 연관 메서드 내에서 notice.setProduct(this)
        }

        // 6. 옵션 처리 - 연관관계 포함해서 안전하게 설정
        List<ProductOption> optionList = new ArrayList<>();
        if (dto.getOptions() != null) {
            for (ProductOptionDTO opt : dto.getOptions()) {
                if (opt.getOptionName() != null && !opt.getOptionName().isBlank()) {
                    ProductOption option = ProductOption.builder()
                            .optionName(opt.getOptionName())
                            .optionValue(opt.getOptionValue())
                            .build();
                    option.setProduct(product); // ✅ 연관 설정
                    optionList.add(option);
                }
            }
        }
        product.setOptions(optionList); // 내부에서 clear 후 add, 연관 포함

        // 7. 최종 저장 (productCode 포함)
        return productRepository.save(product);
    }

    @Transactional
    public void modifyProduct(ProductDTO dto) {
        Product product = productRepository.findById(dto.getId()).orElseThrow();

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("카테고리 없음"));
        product.setCategory(category);

        modelMapper.map(dto, product);
        product.setViews(0);

        product.getOptions().clear();
        if (product.getNotice() != null) {
            productNoticeRepository.delete(product.getNotice());
            product.setNotice(null);
        }

        Optional.ofNullable(dto.getOptions()).ifPresent(options -> options.forEach(opt -> {
            ProductOption option = ProductOption.builder()
                    .optionName(opt.getOptionName())
                    .optionValue(opt.getOptionValue())
                    .product(product).build();
            product.getOptions().add(option);
        }));

        if (dto.getNotice() != null) {
            ProductNotice notice = modelMapper.map(dto.getNotice(), ProductNotice.class);
            notice.setProduct(product);
            product.setNotice(notice);
        }

        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductByCode(String code) {
        return productRepository.findWithCategoryByProductCode(code)
                .map(ProductDTO::fromEntity).orElse(null);
    }

    @Transactional
    public void deleteProduct(String code) {
        Product product = productRepository.findWithCategoryByProductCode(code)
                .orElseThrow(() -> new RuntimeException("상품 없음"));
        productRepository.delete(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> searchProducts(String field, String keyword, Pageable pageable) {

        if (field == null) field = "";
        if (keyword == null) keyword = "";

        Page<Product> products = switch (field) {
            case "name" -> productRepository.findByNameContaining(keyword, pageable);
            case "productCode" -> productRepository.findByProductCodeContaining(keyword, pageable);
            case "companyName" -> productRepository.findByCompanyNameContaining(keyword, pageable);
            default -> productRepository.findAll(pageable);
        };
        return products.map(ProductDTO::fromEntity);
    }

    @Cacheable(value = "getPopularProductsWithReview")
    @Transactional(readOnly = true)
    public List<ProductDTO> getPopularProductsWithReview() {
        Pageable top4 = PageRequest.of(0, 4);
        List<String> codes = orderItemRepository.findTopPopularProductCodes(top4);
        if (codes.isEmpty()) return List.of();

        List<Product> products = productRepository.findAllByProductCodeInWithOptions(codes);
        Map<String, Product> map = products.stream()
                .collect(Collectors.toMap(Product::getProductCode, p -> p));

        Map<String, ReviewSummary> reviewMap = reviewRepository.getReviewSummaries().stream()
                .collect(Collectors.toMap(ReviewSummary::getCode, r -> r));

        return codes.stream()
                .map(map::get)
                .filter(Objects::nonNull)
                .map(p -> mapToDTOWithReviewData(p, reviewMap)) // ✅ 리뷰 데이터 들어감
                .toList();
    }

    @Cacheable(value = "getBest10Products")
    @Transactional(readOnly = true)
    public List<ProductDTO> getBest10Products() {
        Pageable top10 = PageRequest.of(0, 10);
        List<String> codes = orderItemRepository.findTopPopularProductCodes(top10);
        if (codes.isEmpty()) return List.of();

        List<Product> products = productRepository.findAllByProductCodeInWithOptions(codes);
        Map<String, Product> map = products.stream()
                .collect(Collectors.toMap(Product::getProductCode, p -> p));

        // ⭐ 리뷰 데이터 한 번에 조회
        Map<String, ReviewSummary> reviewMap = reviewRepository.getReviewSummaries().stream()
                .peek(rs -> log.warn("📦 review summary: {}", rs))
                .collect(Collectors.toMap(ReviewSummary::getCode, rs -> rs));

        return codes.stream()
                .map(map::get)
                .filter(Objects::nonNull)
                .map(product -> mapToDTOWithReviewData(product, reviewMap)) // ⭐ 수정됨
                .toList();
    }

    @Cacheable(value = "getSortedAllProducts")
    @Transactional(readOnly = true)
    public List<ProductDTO> getSortedAllProducts(String sort) {
        List<Product> products = productRepository.findAllWithFetchJoin();

        // 리뷰 데이터 조회 (한 번에)
        Map<String, ReviewSummary> reviewMap = reviewRepository.getReviewSummaries().stream()
                .collect(Collectors.toMap(ReviewSummary::getCode, rs -> rs));

        Comparator<Product> comparator = switch (sort) {
            case "review" -> Comparator.comparingInt((Product p) ->
                    Optional.ofNullable(reviewMap.get(p.getProductCode()))
                            .map(r -> r.getReviewCount().intValue()).orElse(0)).reversed();

            case "rating" -> Comparator.comparingDouble((Product p) ->
                    Optional.ofNullable(reviewMap.get(p.getProductCode()))
                            .map(ReviewSummary::getAvgRating).orElse(0.0)).reversed();

            case "low" -> Comparator.comparing(this::getDiscountedPrice);
            case "high" -> Comparator.comparing(this::getDiscountedPrice).reversed();
            case "sale" -> Comparator.comparingInt(Product::getStock).reversed(); // 임시 대체
            default -> Comparator.comparing(Product::getId).reversed();
        };

        return products.stream()
                .sorted(comparator)
                .map(p -> mapToDTOWithReviewData(p, reviewMap))
                .toList();
    }

    @Cacheable(value = "getDiscountedProductsWithReview")
    @Transactional(readOnly = true)
    public List<ProductDTO> getDiscountedProductsWithReview() {
        List<Product> products = productRepository.findAllWithFetchJoinWhereDiscountOver20();

        Map<String, ReviewSummary> reviewMap = reviewRepository.getReviewSummaries().stream()
                .collect(Collectors.toMap(ReviewSummary::getCode, r -> r));

        return products.stream()
                .map(p -> mapToDTOWithReviewData(p, reviewMap))
                .toList();
    }

    @Cacheable(value = "getBest10DiscountedProducts")
    public List<ProductDTO> getBest10DiscountedProducts() {
        Pageable top10 = PageRequest.of(0, 100);
        List<String> codes = orderItemRepository.findTopPopularProductCodes(top10);
        if (codes.isEmpty()) return List.of();

        List<Product> products = productRepository.findAllByProductCodeInWithOptions(codes);
        return products.stream().filter(p -> p.getDiscount() >= 20)
                .limit(10).map(ProductDTO::fromEntity).toList();
    }

    @Cacheable(value = "getSortedProductsByCategories")
    public List<ProductDTO> getSortedProductsByCategories(List<Long> categoryIds, String sort) {
        List<Product> products = productRepository.findAllByCategory_CategoryIdInWithOptions(categoryIds);
        return products.stream().sorted(getComparator(sort)).map(ProductDTO::fromEntity).toList();
    }

    @Cacheable(value = "getBest10ProductsByCategoryId")
    public List<ProductDTO> getBest10ProductsByCategoryId(Long categoryId) {
        Pageable top10 = PageRequest.of(0, 10);
        List<String> codes = productRepository.findTopPopularProductCodesByCategoryId(categoryId, top10);
        if (codes.isEmpty()) return List.of();

        List<Product> products = productRepository.findAllByProductCodeInWithOptions(codes);
        Map<String, Product> map = products.stream().collect(Collectors.toMap(Product::getProductCode, p -> p));
        return codes.stream().map(map::get).filter(Objects::nonNull).map(ProductDTO::fromEntity).toList();
    }

    @Cacheable(value = "getBest10ProductsByCategories")
    public List<ProductDTO> getBest10ProductsByCategories(List<Long> categoryIds) {
        Pageable top10 = PageRequest.of(0, 10);
        List<String> codes = orderItemRepository.findTopPopularProductCodesByCategoryIds(categoryIds, top10);
        if (codes.isEmpty()) return List.of();

        List<Product> products = productRepository.findAllByProductCodeInWithOptions(codes);
        Map<String, Product> map = products.stream().collect(Collectors.toMap(Product::getProductCode, p -> p));
        return codes.stream().map(map::get).filter(Objects::nonNull).map(ProductDTO::fromEntity).toList();
    }

    @Cacheable(value = "sortedProductByCate")
    @Transactional(readOnly = true)
    public List<ProductDTO> getSortedProductsByCategory(Long categoryId, String sort) {
        List<Product> products = productRepository.findWithFetchJoinByCategoryIdOrderByIdDesc(categoryId);

        Map<String, ReviewSummary> reviewMap = reviewRepository.getReviewSummaries().stream()
                .collect(Collectors.toMap(ReviewSummary::getCode, rs -> rs));

        Comparator<Product> comparator = switch (sort) {
            case "review" -> Comparator.comparingInt((Product p) ->
                    Optional.ofNullable(reviewMap.get(p.getProductCode()))
                            .map(r -> r.getReviewCount().intValue()).orElse(0)).reversed();

            case "rating" -> Comparator.comparingDouble((Product p) ->
                    Optional.ofNullable(reviewMap.get(p.getProductCode()))
                            .map(ReviewSummary::getAvgRating).orElse(0.0)).reversed();

            case "low" -> Comparator.comparing(this::getDiscountedPrice);
            case "high" -> Comparator.comparing(this::getDiscountedPrice).reversed();
            case "sale" -> Comparator.comparingInt(Product::getStock).reversed(); // 임시
            default -> Comparator.comparing(Product::getId).reversed();
        };

        return products.stream()
                .sorted(comparator)
                .map(p -> mapToDTOWithReviewData(p, reviewMap))
                .toList();
    }

    @Cacheable(value = "searchCache")
    public List<ProductDTO> searchingProducts(String nameKeyword, String companyNameKeyword) {
        List<Product> products = productRepository.findByNameContainingIgnoreCaseOrCompanyNameContainingIgnoreCase(
                nameKeyword, companyNameKeyword
        );

        return products.stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @CacheEvict(value = "searchCache", allEntries = true)
    public void deleteSearchCache() {}

    @CacheEvict(value = "sortedProductByCate", allEntries = true)
    public void deleteSortedProductByCate() {}

    @CacheEvict(value = "getBest10ProductsByCategories", allEntries = true)
    public void deleteGetBest10ProductsByCategories() {}

    @CacheEvict(value = "getBest10ProductsByCategoryId", allEntries = true)
    public void deleteGetBest10ProductsByCategoryId() {}

    @CacheEvict(value = "getSortedProductsByCategories", allEntries = true)
    public void deleteGetSortedProductsByCategories() {}

    @CacheEvict(value = "getBest10DiscountedProducts", allEntries = true)
    public void deleteGetBest10DiscountedProducts() {}

    @CacheEvict(value = "getDiscountedProductsWithReview", allEntries = true)
    public void deleteGetDiscountedProductsWithReview() {}

    @CacheEvict(value = "getSortedAllProducts", allEntries = true)
    public void deleteGetSortedAllProducts() {}

    @CacheEvict(value = "getPopularProductsWithReview", allEntries = true)
    public void deleteGetPopularProductsWithReview() {}

    public List<String> autoCompleteProductNames(String keyword) {
        return productRepository.findTop10ByNameContainingIgnoreCase(keyword)
                .stream()
                .map(Product::getName)
                .toList();
    }
}
