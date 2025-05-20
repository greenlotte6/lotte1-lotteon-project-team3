package kr.co.lotteOn.dto;

import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Order;
import kr.co.lotteOn.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResultDTO implements Serializable {

    private String orderCode;
    private String memberId;
    private String productName;
    private int quantity;
    private int finalPrice;
    private String receiver;
    private String addr;
    private String hp;
    private String payment;
    private String discount;
    private String fee;
    private Long actualMoney;
    private int finalTotal;
    private int couponPointDiscount;

    private Member member;

    public static OrderResultDTO fromEntity(Order order, OrderItem item) {
        return OrderResultDTO.builder()
                .orderCode(order.getOrderCode())
                .memberId(order.getMember().getId())
                .productName(item.getProduct().getName()) // 여기서 상품명 가져옴
                .quantity(item.getQuantity())
                .finalPrice(item.getTotal()) // 또는 order.getActualMoney() 써도 됨
                .receiver(order.getReceiver())
                .addr(order.getDelivery())
                .hp(order.getMember().getHp())
                .payment(order.getPayment())
                .discount(order.getDiscount())
                .fee(order.getFee())
                .member(order.getMember())
                .actualMoney(order.getActualMoney())
                .build();
    }
}
