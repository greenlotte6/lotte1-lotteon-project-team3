package kr.co.lotteOn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Product")
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String productCode;

    private String name;
    private String description;
    private String maker;
    private int price;
    private int discount;
    private int point;
    private int stock;
    private int deliveryFee;
    private String companyName;

    private String imageList;
    private String imageMain;
    private String imageDetail;
    private String imageInfo;

    private int views;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ProductOption> options = new ArrayList<>();

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private ProductNotice notice;
}