package kr.co.lotteOn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Coupon")
public class Coupon {

    @Id
    private String couponCode;  //쿠폰 코드 -> 자동생성

    private String couponType;  //쿠폰종류
    private String couponName;  //쿠폰명

    @Column(unique = true)  // 이 컬럼은 유니크 제약을 가짐
    private int issuedNo;   //쿠폰 고유 번호

    private String benefit;     //혜택
    private String startDate;   //사용기간 시작
    private String endDate;     //사용기간 종료
    private String companyName; //발급자(회사)
    private int issueCount; //발급수

    @Column(columnDefinition = "INT DEFAULT 0")
    private int useCount;   //사용수

    private String status;  //발급중, 발급종료

    @CreationTimestamp
    private LocalDateTime issuedDate;   //발급일

    private String management;
    private String etc; //쿠폰 추가 정보(유의사항 등)
    
    @PrePersist
    public void prePersist(){
        if(this.status == null){
            this.status = "발급중";
        }
    }

    public void increaseUseCount() {
        if (this.useCount >= this.issueCount) {
            throw new IllegalStateException("쿠폰 발급 수량을 초과했습니다.");
        }

        this.useCount++;

        if (this.useCount == this.issueCount) {
            this.status = "발급 종료";
        }
    }


}
