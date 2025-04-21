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
@Table(name = "Qna")
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int qnaNo;    //글번호

    private String cate1;       //카테고리 : 회원, 이벤트, 주문, 배송, 취소, 여행, 안전거래
    private String cate2;       //2차 카테고리
    private String title;       //글제목
    private String content;     //글내용
    private String views;       //조회수
    private String regDate;     //등록날짜
    private String status;      //NULL
    private String writer;      //작성자 = 관리자(Admin)
}

