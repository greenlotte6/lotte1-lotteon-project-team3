package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.ProductDTO;
import kr.co.lotteOn.dto.ProductNoticeDTO;
import kr.co.lotteOn.dto.ProductOptionDTO;
import kr.co.lotteOn.entity.*;
import kr.co.lotteOn.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
        if ("low".equals(sort)) {
            return Comparator.comparing(this::getDiscountedPrice);
        } else if ("high".equals(sort)) {
            return Comparator.comparing(this::getDiscountedPrice, Comparator.reverseOrder());
        } else {
            return Comparator.comparing(Product::getId).reversed();
        }
    }

    private int getDiscountedPrice(Product p) {
        int price = p.getPrice();
        int discount = p.getDiscount(); // 퍼센트
        return price - (price * discount / 100);
    }



    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public Product saveProduct(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setDiscount(dto.getDiscount());
        product.setPoint(dto.getPoint());
        product.setStock(dto.getStock());
        product.setDeliveryFee(dto.getDeliveryFee());
        product.setCompanyName(dto.getCompanyName());
        product.setViews(0);

        // 이미지 파일명 설정
        product.setImageList(dto.getImageListFile().getOriginalFilename());
        product.setImageMain(dto.getImageMainFile().getOriginalFilename());
        product.setImageDetail(dto.getImageDetailFile().getOriginalFilename());
        product.setImageInfo(dto.getImageInfoFile().getOriginalFilename());

        // 카테고리 설정
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("카테고리 없음"));
        product.setCategory(category);

        // 상품 저장
        Product saved = productRepository.save(product);

        // 상품코드 생성
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String code = String.format("P%s%04d", today, saved.getId());
        saved.setProductCode(code);

        //코드 반영을 위한 재저장
        productRepository.save(saved);

        // 옵션 저장
        if (dto.getOptions() != null) {
            for (ProductOptionDTO opt : dto.getOptions()) {
                if (opt.getOptionName() != null && !opt.getOptionName().trim().isEmpty()) {
                    ProductOption option = new ProductOption();
                    option.setOptionName(opt.getOptionName());
                    option.setOptionValue(opt.getOptionValue());
                    option.setProduct(saved);
                    productOptionRepository.save(option);
                }
            }
        }

        // 고시 저장
        if (dto.getNotice() != null) {
            ProductNoticeDTO ndto = dto.getNotice();
            ProductNotice notice = new ProductNotice();
            notice.setProduct(saved);
            notice.setProdStatus(ndto.getProdStatus());
            notice.setVatYn(ndto.getVatYn());
            notice.setReceiptYn(ndto.getReceiptYn());
            notice.setBusinessType(ndto.getBusinessType());
            notice.setOrigin(ndto.getOrigin());
            productNoticeRepository.save(notice);
        }

        return saved;
    }

    @Transactional
    public void modifyProduct(ProductDTO productDTO) {
        Product product = productRepository.findById(productDTO.getId()).orElseThrow();

        Category selectedCategory = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("카테고리 없음"));
        product.setCategory(selectedCategory);

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setCompanyName(productDTO.getCompanyName());
        product.setPrice(productDTO.getPrice());
        product.setDiscount(productDTO.getDiscount());
        product.setPoint(productDTO.getPoint());
        product.setStock(productDTO.getStock());
        product.setDeliveryFee(productDTO.getDeliveryFee());
        product.setViews(0);
        // 기존 옵션, 공지 제거 후 다시 세팅
        product.getOptions().clear();
        
        if (product.getNotice() != null) {
            productNoticeRepository.delete(product.getNotice()); //기존 Notice 삭제
            product.setNotice(null); //연결 끊기
        }
        productDTO.getOptions().forEach(optDto -> {
            ProductOption option = ProductOption.builder()
                    .optionName(optDto.getOptionName())
                    .optionValue(optDto.getOptionValue())
                    .product(product)
                    .build();
            product.getOptions().add(option);
        });

        if (productDTO.getNotice() != null) {
            ProductNotice notice = ProductNotice.builder()
                    .prodStatus(productDTO.getNotice().getProdStatus())
                    .vatYn(productDTO.getNotice().getVatYn())
                    .receiptYn(productDTO.getNotice().getReceiptYn())
                    .businessType(productDTO.getNotice().getBusinessType())
                    .origin(productDTO.getNotice().getOrigin())
                    .product(product)
                    .build();
            product.setNotice(notice);
        }
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductByCode(String productCode) {
        return productRepository.findWithCategoryByProductCode(productCode)
                .map(ProductDTO::fromEntity)
                .orElse(null);
    }

    public void deleteProduct(String productCode) {
        Product product = productRepository.findWithCategoryByProductCode(productCode)
                .orElseThrow(() -> new RuntimeException("상품 없음"));
        productRepository.delete(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> searchProducts(String field, String keyword, Pageable pageable) {
        Page<Product> products;

        if (field == null || keyword == null || keyword.isBlank()) {
            products = productRepository.findAll(pageable);
        } else {
            products = switch (field) {
                case "name" -> productRepository.findByNameContaining(keyword, pageable);
                case "productCode" -> productRepository.findByProductCodeContaining(keyword, pageable);
                case "companyName" -> productRepository.findByCompanyNameContaining(keyword, pageable);
                default -> productRepository.findAll(pageable);
            };
        }
        return products.map(ProductDTO::fromEntity);
    }
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategory_CategoryId(categoryId);
        return products.stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getPopularProducts() {
        Pageable top4 = PageRequest.of(0, 4);
        List<String> codes = orderItemRepository.findTopPopularProductCodes(top4);

        if (codes.isEmpty()) return List.of();

        List<Product> products = productRepository.findAllByProductCodeInWithOptions(codes);

        // 인기순 정렬 유지 (productCode 기준)
        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductCode, p -> p));

        return codes.stream()
                .map(productMap::get)
                .filter(Objects::nonNull)
                .map(ProductDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getBest10Products() {
        Pageable top10 = PageRequest.of(0, 10);
        List<String> codes = orderItemRepository.findTopPopularProductCodes(top10);
        if (codes.isEmpty()) return List.of();

        List<Product> products = productRepository.findAllByProductCodeInWithOptions(codes);
        Map<String, Product> map = products.stream()
                .collect(Collectors.toMap(Product::getProductCode, p -> p));

        return codes.stream()
                .map(map::get)
                .filter(Objects::nonNull)
                .map(ProductDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getBest10ProductsByCategoryId(Long categoryId) {
        Pageable top10 = PageRequest.of(0, 10);
        List<String> codes = productRepository.findTopPopularProductCodesByCategoryId(categoryId, top10);

        if (codes.isEmpty()) return List.of();

        List<Product> products = productRepository.findAllByProductCodeInWithOptions(codes);
        Map<String, Product> map = products.stream()
                .collect(Collectors.toMap(Product::getProductCode, p -> p));

        return codes.stream()
                .map(map::get)
                .filter(Objects::nonNull)
                .map(ProductDTO::fromEntity)
                .toList();
    }


    public List<ProductDTO> getSortedAllProducts(String sort) {
        if ("sale".equals(sort)) {
            Pageable top = PageRequest.of(0, 100);
            List<String> codes = orderItemRepository.findTopPopularProductCodes(top);
            List<Product> products = productRepository.findAllByProductCodeInWithOptions(codes);
            Map<String, Product> map = products.stream()
                    .collect(Collectors.toMap(Product::getProductCode, p -> p));
            return codes.stream().map(map::get).filter(Objects::nonNull).map(ProductDTO::fromEntity).toList();

        } else if ("low".equals(sort)) {
            return productRepository.findAllOrderByDiscountedPriceAsc()
                    .stream().map(ProductDTO::fromEntity).toList();

        } else if ("high".equals(sort)) {
            return productRepository.findAllOrderByDiscountedPriceDesc()
                    .stream().map(ProductDTO::fromEntity).toList();

        } else {
            return productRepository.findAllWithFetchJoinOrderByIdDesc()
                    .stream()
                    .map(product -> {
                        ProductDTO dto = ProductDTO.fromEntity(product);
                        double avgRating = reviewRepository.getAverageRatingByProductCode(product.getProductCode());
                        dto.setAvgRating(avgRating);
                        return dto;
                    })
                    .toList();
        }
    }


    public List<ProductDTO> getSortedProductsByCategory(Long categoryId, String sort) {
        if ("sale".equals(sort)) {
            Pageable top = PageRequest.of(0, 100);
            List<String> codes = productRepository.findTopPopularProductCodesByCategoryId(categoryId, top);
            List<Product> products = productRepository.findAllByProductCodeInWithOptions(codes);
            Map<String, Product> map = products.stream()
                    .collect(Collectors.toMap(Product::getProductCode, p -> p));
            return codes.stream().map(map::get).filter(Objects::nonNull).map(ProductDTO::fromEntity).toList();
        } else if ("low".equals(sort)) {
            return productRepository.findByCategoryWithDiscountedPriceAsc(categoryId)
                    .stream().map(ProductDTO::fromEntity).toList();
        } else if ("high".equals(sort)) {
            return productRepository.findByCategoryWithDiscountedPriceDesc(categoryId)
                    .stream().map(ProductDTO::fromEntity).toList();
        } else {
            return productRepository.findWithFetchJoinByCategoryIdOrderByIdDesc(categoryId)
                    .stream().map(ProductDTO::fromEntity).toList();
        }
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getDiscountedProducts() {
        List<Product> products = productRepository.findAllWithFetchJoinWhereDiscountOver20();
        return products.stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getBest10DiscountedProducts() {
        Pageable top10 = PageRequest.of(0, 100); // 먼저 판매량 높은 상품 Top100 가져옴
        List<String> codes = orderItemRepository.findTopPopularProductCodes(top10);
        if (codes.isEmpty()) return List.of();

        // 상품 + 옵션 + 카테고리 다 fetch join으로 가져옴
        List<Product> products = productRepository.findAllByProductCodeInWithOptions(codes);

        // 필터링: 할인율 20% 이상만
        List<Product> filtered = products.stream()
                .filter(p -> p.getDiscount() >= 20)
                .limit(10) // 여기서 상위 10개로 제한
                .toList();

        return filtered.stream()
                .map(ProductDTO::fromEntity)
                .toList();
    }

    // 다중 카테고리 기준 정렬된 상품
    public List<ProductDTO> getSortedProductsByCategories(List<Long> categoryIds, String sort) {
        List<Product> products = productRepository.findAllByCategory_CategoryIdInWithOptions(categoryIds);
        // 정렬 기준 적용 - 예: discountPrice 기준으로 정렬
        return products.stream()
                .sorted(getComparator(sort))
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getBest10ProductsByCategories(List<Long> categoryIds) {
        Pageable top10 = PageRequest.of(0, 10);
        List<String> codes = orderItemRepository.findTopPopularProductCodesByCategoryIds(categoryIds, top10);
        if (codes.isEmpty()) return List.of();

        List<Product> products = productRepository.findAllByProductCodeInWithOptions(codes);
        Map<String, Product> map = products.stream().collect(Collectors.toMap(Product::getProductCode, p -> p));

        return codes.stream()
                .map(map::get)
                .filter(Objects::nonNull)
                .map(ProductDTO::fromEntity)
                .toList();
    }


    public String getProductNameByCode(String productCode) {
        return productRepository.findByProductCode(productCode)
                .map(Product::getName)
                .orElse("상품명 없음");
    }


}
