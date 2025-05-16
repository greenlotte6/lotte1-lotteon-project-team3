package kr.co.lotteOn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "`Order`")
public class Order {

    @Id
    private String orderCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", referencedColumnName = "id")
    private Member member;

    private String name;
    private String delivery;
    private String payment;
    private String discount;
    private String fee;
    private Long actualMoney;
    private String orderStatus;
    private String refundStatus;

    @Column(nullable = false)
    private String receiver;

    @Builder.Default
    private LocalDateTime orderDate = LocalDateTime.now();

    private String confirm;
    private String etc;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();
    
}

