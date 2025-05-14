package kr.co.lotteOn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "IssuedCoupon")
public class IssuedCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int issuedNo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="couponCode")
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="memberId")
    private Member member;

    @Builder.Default
    private boolean used = false;

    //사용된 주문 코드
    @ManyToOne
    @JoinColumn(name = "orderCode", referencedColumnName = "orderCode")
    private Order order;
    
    private LocalDateTime useDate;

    @CreationTimestamp
    private LocalDateTime issuedDate;

    private LocalDateTime expiredDate;
    private String status;



}
