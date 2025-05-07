package kr.co.lotteOn.dto;

import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Order;
import kr.co.lotteOn.util.OrderCodeGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {

    private String memberId;
    private String receiver;
    private String sender;
    private String hp;

    private String zip;
    private String addr1;
    private String addr2;
    private String other;

    private String payment;
    private int totalPrice;
    private int deliveryFee;
    private int discountAmount;
    private int finalTotal;

    private String productCode;
    private String quantity;
    private String option;
    private String issuedNo;
    private int usedPoint;

    public Order toEntity(Member member) {
        return Order.builder()
                .orderCode(OrderCodeGenerator.generateOrderCode())
                .member(member)
                .name(receiver)
                .productCode(productCode)
                .quantity(quantity)
                .totalPrice(String.valueOf(totalPrice))
                .payment(payment)
                .orderStatus("결제완료")
                .delivery(zip + " " + addr1 + " " + addr2 + (other != null ? " " + other : ""))
                .discount(String.valueOf(discountAmount))
                .fee(String.valueOf(deliveryFee))
                .actualMoney(String.valueOf(finalTotal))
                .build();
    }


}
