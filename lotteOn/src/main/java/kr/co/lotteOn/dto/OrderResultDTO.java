package kr.co.lotteOn.dto;

import kr.co.lotteOn.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResultDTO {

    private String orderCode;
    private String memberId;
    private String productName;
    private int quantity;
    private int finalPrice;
    private String receiver;
    private String addr;
    private String hp;
    private String payment;

    public static OrderResultDTO fromEntity(Order order) {
        return OrderResultDTO.builder()
                .orderCode(order.getOrderCode())
                .memberId(order.getMember().getId())
                .productName(order.getProductCode()) // 실제 상품 이름이 아니라면 별도 처리 필요
                .quantity(Integer.parseInt(order.getQuantity()))
                .finalPrice(Integer.parseInt(order.getActualMoney()))
                .receiver(order.getName())
                .addr(order.getDelivery())
                .hp(order.getMember().getHp())
                .payment(order.getPayment())
                .build();

    }
}
