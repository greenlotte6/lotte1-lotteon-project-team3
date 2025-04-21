package kr.co.lotteOn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FaqDTO {

    private int qnaNo;    //글번호

    private String cate1;       //카테고리 : 회원, 이벤트, 주문, 배송, 취소, 여행, 안전거래
    private String cate2;       //2차 카테고리
    private String title;       //글제목
    private String content;     //글내용
    private String views;       //조회수
    private String regDate;     //등록날짜
    private String status;      //NULL
    private String writer;      //작성자 = 관리자(Admin)
    private String comment;     //답변?
}
