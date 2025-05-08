package kr.co.lotteOn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="Shop")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shopId;

    private String companyName;
    private String delegate;
    private String businessNo;
    private String communicationNo;
    private String shopHp;

    private String status;
    private String management;

    @ManyToOne
    @JoinColumn(name= "sellerId")
    private Seller seller;

    @PrePersist //Default값 세팅
    public void prePersist() {
        if (this.status == null) this.status = "운영중";

    }

}



