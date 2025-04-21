package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.MemberDTO;
import kr.co.lotteOn.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    //회원 - 로그인
    @GetMapping("/login")
    public String login() {
        return "/member/login";
    }

    //회원 - 회원가입
    @GetMapping("/register")
    public String view() {
        return "/member/register";
    }
    @PostMapping("/register")
    public String register(MemberDTO memberDTO) {
        memberService.register(memberDTO);
        return "redirect:/member/login";
    }

    //회원 - 구분
    @GetMapping("/join")
    public String join() {
        return "/member/join";
    }

    //회원 - 약관
    @GetMapping("/signUp")
    public String signUp() {
        return "/member/signUp";
    }

    //회원 - 약관(사업자)
    @GetMapping("/signUp_seller")
    public String signUp_seller() {
        return "/member/signUp_seller";
    }

    //회원 - 판매자가입
    @GetMapping("/registerSeller")
    public String registerSeller() {
        return "/member/registerSeller";
    }
    /* **************************회원 끝*********************************** */
}
