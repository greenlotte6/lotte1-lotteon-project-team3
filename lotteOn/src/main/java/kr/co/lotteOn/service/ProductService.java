package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.ProductDTO;
import kr.co.lotteOn.dto.ProductNoticeDTO;
import kr.co.lotteOn.dto.ProductOptionDTO;
import kr.co.lotteOn.entity.Category;
import kr.co.lotteOn.entity.Product;
import kr.co.lotteOn.entity.ProductNotice;
import kr.co.lotteOn.entity.ProductOption;
import kr.co.lotteOn.repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

    public Product saveProduct(ProductDTO dto) {
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
        saved.setProductCode("P" + today + String.format("%04d", saved.getId()));

        // 옵션 저장
        if (dto.getOptions() != null) {
            for (ProductOptionDTO opt : dto.getOptions()) {
                ProductOption option = new ProductOption();
                option.setOptionName(opt.getOptionName());
                option.setOptionValue(opt.getOptionValue());
                option.setProduct(saved);
                productOptionRepository.save(option);
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
}

