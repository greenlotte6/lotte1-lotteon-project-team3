package kr.co.lotteOn.dto;

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
    private MultipartFile imageListFile;
    private MultipartFile imageMainFile;
    private MultipartFile imageDetailFile;

    private List<ProductOptionDTO> options;
    private ProductNoticeDTO notice;

    private Long categoryId;
}