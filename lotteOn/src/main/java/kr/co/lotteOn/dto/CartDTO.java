package kr.co.lotteOn.dto;

import jakarta.persistence.*;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO implements Serializable {

    private Long id;
    private Long productId;
    private String productName;
    private String productImage;
    private int quantity;
    private int price;
    private int discount;
    private int deliveryFee;
    private String productOption;
    private String productCode;
}
