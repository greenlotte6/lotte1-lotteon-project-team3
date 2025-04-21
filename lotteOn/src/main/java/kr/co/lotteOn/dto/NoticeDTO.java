package kr.co.lotteOn.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeDTO {

    private int noticeNo;    //글번호

    private String cate;        //카테고리 : 고객서비스, 이벤트당첨, 안전거래, 위해상품
    private String title;       //글제목
    private String content;     //글내용
    private String views;       //조회수
    private String regDate;     //등록날짜
    private String status;      //NULL
    private String writer;      //작성자 = 관리자(Admin)

    //추가필드
    private MemberDTO member;

}
