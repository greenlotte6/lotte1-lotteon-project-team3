package kr.co.lotteOn.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
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
@Table(name = "Seller")
public class Seller {

    @Id
    private String sellerId;
    private String password;
    private String companyName;
    private String delegate;
    private String businessNo;
    private String communicationNo;
    private String rating;
    private String hp;
    private String fax;
    private String zip;
    private String addr1;
    private String addr2;

    @CreationTimestamp
    private LocalDateTime regDate;
    private LocalDateTime leaveDate;

    @PrePersist //Default값 세팅
    public void prePersist() {
        if (this.rating == null) this.rating = "BRONZE";
    }
}