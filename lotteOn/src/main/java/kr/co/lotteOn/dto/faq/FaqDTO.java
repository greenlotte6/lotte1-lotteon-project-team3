package kr.co.lotteOn.dto.faq;

import kr.co.lotteOn.dto.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FaqDTO {

    private int faqNo;    //글번호

    private String cate1;       //카테고리 : 회원, 이벤트, 주문, 배송, 취소, 여행, 안전거래
    private String cate2;       //2차 카테고리
    private String title;       //글제목
    private String content;     //글내용
    private String views;       //조회수
    private String regDate;     //등록날짜
    private String status;      //NULL
    private String writer;      //작성자 = 관리자(Admin)

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
            default: return "알 수 없음";
        }
    }

    public String getCate2Name() {
        switch (this.cate2) {
            case "가입": return "register";
            case "탈퇴": return "leave";
            case "회원정보": return "information";
            case "로그인": return "login";
            case "쿠폰/할인혜택": return "coupon";
            case "포인트": return "point";
            case "제휴": return "affiliate";
            case "이벤트": return "event";
            case "상품": return "product";
            case "영수증/증빙": return "receipt";
            case "구매내역": return "purchase";
            case "결제": return "payment";
            case "배송상태/기간": return "deliveryStatus";
            case "배송정보확인/변경": return "deliveryInfo";
            case "해외배송": return "abroad";
            case "당일배송": return "day";
            case "해외직구": return "abroadDirect";
            case "반품신청/철회": return "refund";
            case "반품정보확인/변경": return "refundInfo";
            case "교환AS신청/철회": return "exchange";
            case "취소신청/철회": return "cancel";
            case "취소확인/환불정보": return "cancelConfirm";
            case "항공": return "flight";
            case "여행/숙박": return "travel";
            case "서비스 이용규칙 위반": return "service";
            case "지식재산권 침해": return "knowledge";
            case "법령 및 정책위반 상품": return "lwa";
            case "게시물정책위반": return "posting";
            case "직거래/외부거래유도": return "trade";
            case "표시광고": return "advertise";
            case "청소년위해상품/이미지": return "image";
            default: return "알 수 없음";
        }
    }


}
