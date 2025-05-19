package kr.co.lotteOn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String productCode;

    private String name;
    private String description;
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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductOption> options = new ArrayList<>();

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductNotice notice;

    public void setNotice(ProductNotice notice) {
        this.notice = notice;
        if (notice != null) {
            notice.setProduct(this);
        }
    }
    public void setOptions(List<ProductOption> options) {
        this.options.clear(); // 기존 리스트 비우고
        if (options != null) {
            for (ProductOption option : options) {
                option.setProduct(this);
                this.options.add(option); // 새로운 옵션 추가
            }
        }
    }

    // equals/hashCode는 id 기준
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}