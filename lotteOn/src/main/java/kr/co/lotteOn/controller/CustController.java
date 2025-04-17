package kr.co.lotteOn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/cust")
@Controller
public class CustController {

    //고객센터 - 메인
    @GetMapping("/cust_main")
    public String cust_main(){
        return "/cust/cust_main";
    }
    /* **************************고객센터 메인 끝*********************************** */

    //공지사항 - 메인(전체)
    @GetMapping("/cust_notice_main")
    public String notice_main(){
        return "/cust/cust_notice_main";
    }
    //공지사항 - 위해상품
    @GetMapping("/cust_notice_danger")
    public String notice_danger(){
        return "/cust/cust_notice_danger";
    }
    //공지사항 - 이벤트당첨
    @GetMapping("/cust_notice_event")
    public String notice_event(){
        return "/cust/cust_notice_event";
    }
    //공지사항 - 안전거래
    @GetMapping("/cust_notice_safe")
    public String notice_safe(){
        return "/cust/cust_notice_safe";
    }
    //공지사항 - 고객서비스
    @GetMapping("/cust_notice_service")
    public String notice_service(){
        return "/cust/cust_notice_service";
    }
    //공지사항 - 글보기
    @GetMapping("/cust_notice_view")
    public String notice_view(){
        return "/cust/cust_notice_view";
    }

    /* ***************************공지사항 끝*************************** */

    //자주묻는질문 - 배송
    @GetMapping("/cust_faq_deliver")
    public String faq_deliver(){
        return "/cust/cust_faq_deliver";
    }
    //자주묻는질문 - 쿠폰/이벤트
    @GetMapping("/cust_faq_event")
    public String faq_event(){
        return "/cust/cust_faq_event";
    }
    //자주묻는질문 - 주문/결제
    @GetMapping("/cust_faq_order")
    public String faq_order(){
        return "/cust/cust_faq_order";
    }
    //자주묻는질문 - 취소/반품/교환
    @GetMapping("/cust_faq_refund")
    public String faq_refund(){
        return "/cust/cust_faq_refund";
    }
    //자주묻는질문 - 안전거래
    @GetMapping("/cust_faq_safe")
    public String faq_safe(){
        return "/cust/cust_faq_safe";
    }
    //자주묻는질문 - 여행/숙박/항공
    @GetMapping("/cust_faq_travel")
    public String faq_travel(){
        return "/cust/cust_faq_travel";
    }
    //자주묻는질문 - 회원
    @GetMapping("/cust_faq_user")
    public String faq_user(){
        return "/cust/cust_faq_user";
    }
    //자주묻는질문 - 글보기
    @GetMapping("/cust_faq_view")
    public String faq_view(){
        return "/cust/cust_faq_view";
    }
    /* ***************************자주묻는질문 끝*************************** */

    //문의하기 - 배송
    @GetMapping("/cust_question_deliver")
    public String question_deliver(){
        return "/cust/cust_question_deliver";
    }
    //문의하기 - 쿠폰/이벤트
    @GetMapping("/cust_question_event")
    public String question_event(){
        return "/cust/cust_question_event";
    }
    //문의하기 - 주문/결제
    @GetMapping("/cust_question_order")
    public String question_order(){
        return "/cust/cust_question_order";
    }
    //문의하기 - 취소/반품/교환
    @GetMapping("/cust_question_refund")
    public String question_refund(){
        return "/cust/cust_question_refund";
    }
    //문의하기 - 안전거래
    @GetMapping("/cust_question_safe")
    public String question_safe(){
        return "/cust/cust_question_safe";
    }
    //문의하기 - 여행/숙박/항공
    @GetMapping("/cust_question_travel")
    public String question_travel(){
        return "/cust/cust_question_travel";
    }
    //문의하기 - 회원
    @GetMapping("/cust_question_user")
    public String question_user(){
        return "/cust/cust_question_user";
    }
    //문의하기 - 글보기
    @GetMapping("/cust_question_view")
    public String question_view(){
        return "/cust/cust_question_view";
    }
    //문의하기 - 글쓰기
    @GetMapping("/cust_question_write")
    public String question_write(){
        return "/cust/cust_question_write";
    }
    /* ***************************문의하기 끝*************************** */



}
