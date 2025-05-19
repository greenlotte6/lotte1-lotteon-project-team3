package kr.co.lotteOn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="ProductNotice")
public class ProductNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prodStatus;
    private String vatYn;
    private String receiptYn;
    private String businessType;
    private String origin;

    @OneToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;
}
