package kr.co.lotteOn.dto;

import jakarta.persistence.*;
import kr.co.lotteOn.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOptionDTO implements Serializable {

    private String optionName;
    private String optionValue;

    //,로 value를 구분하는 메서드
    public List<String> getOptionValues(){
        if (this.optionValue == null) return List.of();
        return Arrays.stream(this.optionValue.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

}
