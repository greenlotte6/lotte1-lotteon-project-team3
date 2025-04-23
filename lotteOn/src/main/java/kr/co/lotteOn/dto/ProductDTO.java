package kr.co.lotteOn.dto;

import kr.co.lotteOn.entity.Product;
import kr.co.lotteOn.entity.ProductNotice;
import kr.co.lotteOn.entity.ProductOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long id;
    private String productCode;
    private String name;
    private String description;
    private String maker;
    private Integer price;
    private Integer discount;
    private Integer point;
    private Integer stock;
    private Integer deliveryFee;
    private String companyName;

    // 이미지 업로드용 (Form에서 파일로 받음)
    private MultipartFile imageListFile;
    private MultipartFile imageMainFile;
    private MultipartFile imageDetailFile;

    // 이미지 경로 저장용 (DB에서 조회)
    private String imageList;
    private String imageMain;
    private String imageDetail;

    private int views;
    private Long categoryId;

    private List<ProductOptionDTO> options;
    private ProductNoticeDTO notice;



    public static ProductDTO fromEntity(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .productCode(product.getProductCode())
                .name(product.getName())
                .description(product.getDescription())
                .maker(product.getMaker())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .point(product.getPoint())
                .stock(product.getStock())
                .deliveryFee(product.getDeliveryFee())
                .companyName(product.getCompanyName())
                .imageList(product.getImageList())
                .imageMain(product.getImageMain())
                .imageDetail(product.getImageDetail())
                .views(product.getViews())
                .categoryId(product.getCategory() != null ? product.getCategory().getCategoryId() : null)
                // options, notice는 필요시 추가
                .build();
    }
}