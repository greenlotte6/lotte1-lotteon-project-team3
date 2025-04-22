package kr.co.lotteOn.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name= "Terms")
public class Terms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int termsId;

    @Lob
    private String buyer;

    @Lob
    private String seller;

    @Lob
    private String trade;

    @Lob
    private String place;

    @Lob
    private String privacy;


}
