package kr.co.lotteOn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategorySalesDTO {
    private List<String> labels;      // ["패션", "라이프", "식품", "기타"]
    private List<Integer> sales;      // [100000, 80000, 60000, 20000]
}
