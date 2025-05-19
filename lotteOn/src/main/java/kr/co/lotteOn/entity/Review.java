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
@Table(name = "Review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewNo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "writer")
    private Member member;

    private String productCode;

    private String title;
    private double rating;
    private String content;

    private String image1;
    private String image2;
    private String image3;

    @CreationTimestamp
    private LocalDateTime regDate;

}
