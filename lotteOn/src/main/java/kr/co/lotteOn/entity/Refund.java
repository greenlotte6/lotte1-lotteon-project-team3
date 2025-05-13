package kr.co.lotteOn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Refund")
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int refundNo;


    private String orderCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "memberId")
    private Member member;

    private String refundType;
    private String reason;
    private String image1;
    private String image2;
    private String image3;

    @CreationTimestamp
    private LocalDateTime regDate;

    private String status; //반품요청 완료, 반품승인 완료, 상품 회수중, 반품처리 완료
    private String channel; //반품, 교환

    @PrePersist
    public void prePersist(){
        if(this.status == null){
            this.status = "요청 완료";
        }
    }



}
