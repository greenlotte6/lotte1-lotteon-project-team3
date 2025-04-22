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
@Table(name="ProductOption")
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    private String optionName;
    private String optionValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;
}
