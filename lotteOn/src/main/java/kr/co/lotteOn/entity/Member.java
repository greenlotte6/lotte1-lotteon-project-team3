package kr.co.lotteOn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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
    private String email;
    private String hp;
    private String rating;
    private String role;
    private String zip;
    private String addr1;
    private String addr2;

    @CreationTimestamp
    private LocalDateTime regDate;
    private LocalDateTime leaveDate;

    @PrePersist //Default값 세팅
    public void prePersist() {
        if (this.rating == null) this.rating = "FAMILY";
        if (this.role == null) this.role = "MEMBER";
    }
}