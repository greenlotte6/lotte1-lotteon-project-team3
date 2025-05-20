package kr.co.lotteOn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class OrderChartDTO implements Serializable {

    private List<String> labels;      // 날짜 리스트 (예: 05-11)
    private List<Integer> orders;     // 전체 주문 수
    private List<Integer> confirmed;  // 구매확정 수
    private List<Integer> refunds;    // 반품/취소 수
}
