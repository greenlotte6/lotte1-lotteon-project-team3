package kr.co.lotteOn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Member")
public class Member {

    @Id
    private String id;
    private String password;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private String email;
    private String hp;
    private String rating;
    private String role;
    private String status;
    private String zip;
    private String addr1;
    private String addr2;
    private String another;

    @CreationTimestamp
    private LocalDateTime regDate;
    private LocalDateTime leaveDate;
    private LocalDateTime visitDate;

    // OAuth 인증 업체 정보
    private String provider; // 소셜 로그인 제공자 (Naver, Google 등)
    private String providerId;    // 소셜 로그인 제공자에서 제공하는 고유 ID


    @PrePersist //Default값 세팅
    public void prePersist() {
        if (this.rating == null) this.rating = "FAMILY";
        if (this.role == null) this.role = "MEMBER";
        if (this.status == null) this.status = "정상";
        if (this.another == null) this.another = "없음";
    }
}