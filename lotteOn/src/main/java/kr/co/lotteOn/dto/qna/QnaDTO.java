package kr.co.lotteOn.dto.qna;

import kr.co.lotteOn.dto.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaDTO {

    private int qnaNo;    //글번호

    private String cate1;       //카테고리 : 회원, 이벤트, 주문, 배송, 취소, 여행, 안전거래
    private String cate2;       //2차 카테고리
    private String title;       //글제목
    private String content;     //글내용
    private String views;       //조회수
    private String regDate;     //등록날짜
    private String status;      //NULL
    private String writer;
    private String comment;
    private String channel;

    //추가필드
    private MemberDTO member;



    public String getRegDate(){
        if(regDate != null){
            return regDate.substring(0,10);   // yyyy-mm-dd

        }
        return null;
    }

    // 자동 매핑을 위한 메서드 추가
    public String getCate1Name() {
        switch (this.cate1) {
            case "member": return "회원";
            case "event": return "이벤트/쿠폰";
            case "order": return "주문/결제";
            case "delivery": return "배송";
            case "cancel": return "취소/반품/교환";
            case "travel": return "여행/숙박/항공";
            case "safe": return "안전거래";
            case "product": return "상품";
            case "etc": return "기타";
            default: return "알 수 없음";
        }
    }

    public String getCate1NameBack() {
        switch (this.cate1) {
            case "회원": return "member";
            case "이벤트/쿠폰": return "event";
            case "주문/결제": return "order";
            case "배송": return "delivery";
            case "취소/반품/교환": return "cancel";
            case "여행/숙박/항공": return "travel";
            case "안전거래": return "safe";
            case "상품": return "product";
            case "기타": return "etc";
            default: return "알 수 없음";
        }
    }



}

