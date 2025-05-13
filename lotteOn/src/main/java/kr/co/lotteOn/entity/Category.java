package kr.co.lotteOn.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String name;

    private Integer depth;

    private Integer sortOrder;

    private String useYN;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    @ToString.Exclude
    private Category parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @ToString.Exclude
    @OrderBy("sortOrder ASC")
    private List<Category> children = new ArrayList<>();
}

