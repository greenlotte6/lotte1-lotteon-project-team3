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
public class ProductOptionDTO {

    private String optionName;
    private String optionValue;

}
