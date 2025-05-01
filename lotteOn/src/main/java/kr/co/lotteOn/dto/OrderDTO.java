package kr.co.lotteOn.dto;

import jakarta.persistence.*;
import kr.co.lotteOn.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {

    private String orderCode;
    private Member member;
    private String name;
    private String productCode;
    private String quantity;
    private String totalPrice;
    private String payment;
    private String orderStatus;
    private String orderDate;
    private String delivery;
    private String discount;
    private String fee;
    private String actualMoney;
}

