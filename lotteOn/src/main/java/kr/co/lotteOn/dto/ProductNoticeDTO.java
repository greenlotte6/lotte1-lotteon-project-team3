package kr.co.lotteOn.dto;

import jakarta.persistence.*;
import kr.co.lotteOn.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductNoticeDTO {

    private String prodStatus;
    private String vatYn;
    private String receiptYn;
    private String businessType;
    private String origin;

}
