package kr.co.lotteOn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/member")
@Controller
public class MemberController {

    //회원 - 로그인
    @GetMapping("/login")
    public String login(){
        return "/member/login";
    }
    //회원 - 회원가입
    @GetMapping("/register")
    public String register(){
        return "/member/register";
    }
    //회원 - 구분
    @GetMapping("/join")
    public String join(){
        return "/member/join";
    }
    //회원 - 약관
    @GetMapping("/signUp")
    public String signUp(){
        return "/member/signUp";
    }
    //회원 - 약관(사업자)
    @GetMapping("/signUp_seller")
    public String signUp_seller(){
        return "/member/signUp_seller";
    }
    //회원 - 판매자가입
    @GetMapping("/registerSeller")
    public String registerSeller(){
        return "/member/registerSeller";
    }
    /* **************************회원 끝*********************************** */
}
