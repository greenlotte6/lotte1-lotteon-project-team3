package kr.co.lotteOn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

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

    //추가
    private String status;
    private String management;

    @CreationTimestamp
    private LocalDateTime regDate;
    private LocalDateTime leaveDate;

    @PrePersist //Default값 세팅
    public void prePersist() {
        if (this.rating == null) this.rating = "BRONZE";
        if (this.management == null) this.management = "[중단]";
        if (this.status == null) this.status = "운영중";
    }



//    @OneToMany(mappedBy = "seller")
//    private List<Shop> shops;
}