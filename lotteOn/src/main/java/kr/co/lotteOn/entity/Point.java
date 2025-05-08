package kr.co.lotteOn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "Point")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pointNo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "memberId")
    private Member member;

    private String type;    //SAVE, USE 등등

    private int givePoint;
    private int totalPoint;
    private String giveContent;

    private String orderCode;

    @CreationTimestamp
    private LocalDateTime giveDate;

//    @ManyToOne
//    @JoinColumn(name = "orderCode", referencedColumnName = "orderCode")
//    private Order order;


//    public String getOrderCode() {
//        return order != null ? order.getOrderCode() : null;
//    }


}
