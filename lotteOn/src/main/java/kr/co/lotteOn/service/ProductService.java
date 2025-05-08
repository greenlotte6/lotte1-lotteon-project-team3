package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.ProductDTO;
import kr.co.lotteOn.dto.ProductNoticeDTO;
import kr.co.lotteOn.dto.ProductOptionDTO;
import kr.co.lotteOn.entity.Category;
import kr.co.lotteOn.entity.Product;
import kr.co.lotteOn.entity.ProductNotice;
import kr.co.lotteOn.entity.ProductOption;
import kr.co.lotteOn.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
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

    // 최신 포인트 내역 조회
//    public int getLatestTotalPoint(String memberId) {
//        // 최신 giveDate 기준으로 1개의 totalPoint 가져오기
//        List<Integer> points = pointRepository.findLatestTotalPointByMemberId(memberId, PageRequest.of(0, 1));
//
//        // 리스트가 비어있으면 0 반환, 아니면 첫 번째 값을 반환
//        return points.isEmpty() ? 0 : points.get(0);
//    }
}
