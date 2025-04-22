package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.ProductDTO;
import kr.co.lotteOn.dto.ProductNoticeDTO;
import kr.co.lotteOn.dto.ProductOptionDTO;
import kr.co.lotteOn.entity.Product;
import kr.co.lotteOn.entity.ProductNotice;
import kr.co.lotteOn.entity.ProductOption;
import kr.co.lotteOn.repository.ProductNoticeRepository;
import kr.co.lotteOn.repository.ProductOptionRepository;
import kr.co.lotteOn.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductNoticeRepository productNoticeRepository;

    // ✅ 상품 등록
    public Product saveProduct(ProductDTO dto) {
        // 1. Product Entity 생성
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setMaker(dto.getMaker());
        product.setPrice(dto.getPrice());
        product.setDiscount(dto.getDiscount());
        product.setPoint(dto.getPoint());
        product.setStock(dto.getStock());
        product.setDeliveryFee(dto.getDeliveryFee());
        product.setCompanyName(dto.getCompanyName());
        product.setImageList(dto.getImageList());
        product.setImageMain(dto.getImageMain());
        product.setImageDetail(dto.getImageDetail());
        product.setViews(0);

        // 2. 먼저 저장해서 ID 확보
        Product savedProduct = productRepository.save(product);

        // 3. 상품 코드 생성 (ex. P202504220001)
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String code = String.format("P%s%04d", today, savedProduct.getId());
        savedProduct.setProductCode(code);

        // 4. 옵션 저장
        if (dto.getOptions() != null) {
            for (ProductOptionDTO opt : dto.getOptions()) {
                ProductOption option = new ProductOption();
                option.setOptionName(opt.getOptionName());
                option.setOptionValue(opt.getOptionValue());
                option.setProduct(savedProduct);
                productOptionRepository.save(option);
            }
        }

        // 5. 고시 저장
        if (dto.getNotice() != null) {
            ProductNoticeDTO ndto = dto.getNotice();
            ProductNotice notice = new ProductNotice();
            notice.setProdStatus(ndto.getProdStatus());
            notice.setVatYn(ndto.getVatYn());
            notice.setReceiptYn(ndto.getReceiptYn());
            notice.setBusinessType(ndto.getBusinessType());
            notice.setOrigin(ndto.getOrigin());
            notice.setProduct(savedProduct);
            productNoticeRepository.save(notice);
        }

        return savedProduct;
    }
    // ✅ 상품 단건 조회 (옵션 + 고시 포함)
    @Transactional(readOnly = true)
    public ProductDTO getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("상품이 존재하지 않습니다."));

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setProductCode(product.getProductCode());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setMaker(product.getMaker());
        dto.setPrice(product.getPrice());
        dto.setDiscount(product.getDiscount());
        dto.setPoint(product.getPoint());
        dto.setStock(product.getStock());
        dto.setDeliveryFee(product.getDeliveryFee());
        dto.setCompanyName(product.getCompanyName());
        dto.setImageList(product.getImageList());
        dto.setImageMain(product.getImageMain());
        dto.setImageDetail(product.getImageDetail());
        dto.setViews(product.getViews());

        // 옵션 포함
        List<ProductOptionDTO> options = productOptionRepository.findByProduct(product).stream()
                .map(opt -> {
                    ProductOptionDTO o = new ProductOptionDTO();
                    o.setId(opt.getId());
                    o.setOptionName(opt.getOptionName());
                    o.setOptionValue(opt.getOptionValue());
                    return o;
                }).toList();
        dto.setOptions(options);

        // 고시 포함
        product.getNotice(); // lazy 로딩 방지
        productNoticeRepository.findByProduct(product).ifPresent(notice -> {
            ProductNoticeDTO ndto = new ProductNoticeDTO();
            ndto.setId(notice.getId());
            ndto.setProdStatus(notice.getProdStatus());
            ndto.setVatYn(notice.getVatYn());
            ndto.setReceiptYn(notice.getReceiptYn());
            ndto.setBusinessType(notice.getBusinessType());
            ndto.setOrigin(notice.getOrigin());
            dto.setNotice(ndto);
        });

        return dto;
    }
}

