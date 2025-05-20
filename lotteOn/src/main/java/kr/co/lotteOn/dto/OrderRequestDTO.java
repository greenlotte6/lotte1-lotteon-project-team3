package kr.co.lotteOn.dto;

import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Order;
import kr.co.lotteOn.util.OrderCodeGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO implements Serializable {

    private String memberId;     // ✔ Member 대신 memberId만 받음
    private String name;
    private String delivery;
    private String payment;
    private String discount;
    private String fee;
    private String receiver;
    private Long actualMoney;
    private String productCode;  // ✔ Product 대신 productCode만 받음
    private String quantity;
    private String totalPrice;
    private int usedPoint;
    private Integer issuedNo;
    private List<Long> cartIds;

}
