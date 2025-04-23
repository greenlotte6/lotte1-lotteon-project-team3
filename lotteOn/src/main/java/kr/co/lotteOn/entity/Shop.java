package kr.co.lotteOn.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private int shopId;
    private String companyName;
    private String delegate;
    private String businessNo;
    private String communicationNo;
    private String shopHp;
    private String status;
    private String management;
}


