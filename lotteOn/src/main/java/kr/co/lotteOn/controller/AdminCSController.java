package kr.co.lotteOn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminCSController {

    /*------------ 관리자 - 고객센터 ------------*/

    //고객센터 - 공지사항
    @GetMapping("/cs/noticeList")
    public String noticeList(){
        return "/admin/cs/noticeList";
    }

    //고객센터 - 자주묻는질문
    @GetMapping("/cs/faqList")
    public String faqList(){
        return "/admin/cs/faqList";
    }

    //고객센터 - 문의하기
    @GetMapping("/cs/qnaList")
    public String qnaList(){
        return "/admin/cs/qnaList";
    }

    //고객센터 - 채용정보
    @GetMapping("/cs/recruitList")
    public String recruitList(){
        return "/admin/cs/recruitList";
    }


}
