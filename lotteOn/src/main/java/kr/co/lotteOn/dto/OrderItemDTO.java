package kr.co.lotteOn.dto;


import kr.co.lotteOn.entity.Order;
import kr.co.lotteOn.entity.OrderItem;
import kr.co.lotteOn.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO implements Serializable {

    private String productCode;
    private int quantity;
    private int price;
    private int discount;
    private int total;
    private Long id;
    private String orderCode;
    private List<OrderItemDTO> items;
    
    //추가필드
    private String productName;
    private int point;
    private String imageList;
    private String companyName;

    public static OrderItem toEntity(Order order, Product product, OrderItemDTO dto) {
        return OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(dto.getQuantity())
                .price(dto.getPrice())
                .discount(dto.getDiscount())
                .total(dto.getTotal())
                .build();
    }

//    public static OrderItemDTO fromEntity(OrderItem item) {
//        return OrderItemDTO.builder()
//                .id(item.getId())
//                .orderCode(item.getOrder().getOrderCode())
//                .productCode(item.getProduct().getProductCode())
//                .quantity(item.getQuantity())
//                .price(item.getPrice())
//                .discount(item.getDiscount())
//                .total(item.getTotal())
//                .build();
//    }
//
}
