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
@Table(name = "Notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noticeNo;    //글번호

    private String cate;        //카테고리 : 고객서비스, 이벤트당첨, 안전거래, 위해상품
    private String title;       //글제목
    private String content;     //글내용
    private String views;       //조회수
    private String status;      //NULL

    @CreationTimestamp
    private LocalDateTime regDate;     //등록날짜


    //private String writer;      //작성자 = 관리자(Admin)
    //추가필드
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="writer")
    private Member writer;


}
