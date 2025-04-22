package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.SellerDTO;
import kr.co.lotteOn.dto.TermsDTO;
import kr.co.lotteOn.service.SellerService;
import kr.co.lotteOn.service.TermsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    private final SellerService sellerService;
    private final MemberService memberService;
    private final TermsService termsService;

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
    //동의약관
    public String signUp(Model model) {
        TermsDTO termsDTO= termsService.findTerms();

        if(termsDTO==null) {
            return "redirect:/error/termsNotFound";
        }

        model.addAttribute("termsDTO", termsDTO);
        return "/member/signUp";
    }

    @PostMapping("/signUp")
    public String handleSignUpAgreement(@RequestParam("terms1") boolean terms1,
                                        @RequestParam("terms2") boolean terms2,
                                        @RequestParam("terms3") boolean terms3,
                                        @RequestParam("terms4") boolean terms4) {
        // 모든 약관에 동의했는지 확인
        if (terms1 && terms2 && terms3 && terms4) {
            // 모든 약관에 동의했으면 회원가입 페이지로 리디렉션
            return "redirect:/member/register"; // 회원가입 페이지로 이동
        } else {
            // 약관 동의하지 않으면 경고 메시지나 다시 약관 페이지로 리디렉션
            return "redirect:/member/signUp"; // 약관 동의 페이지로 다시 이동
        }
    }


    //회원 - 약관(사업자)
    @GetMapping("/signUp_seller")
    //동의약관
    public String signUp_seller(Model model) {
        TermsDTO termsDTO= termsService.findTerms();

        if(termsDTO==null) {
            return "redirect:/error/termsNotFound";
        }

        model.addAttribute("termsDTO", termsDTO);
        return "/member/signUp_seller";
    }

    @PostMapping("/signUp_seller")
    public String handleSignUpAgreement2(@RequestParam("terms1") boolean terms1,
                                        @RequestParam("terms2") boolean terms2,
                                        @RequestParam("terms3") boolean terms3,
                                        @RequestParam("terms4") boolean terms4) {
        // 모든 약관에 동의했는지 확인
        if (terms1 && terms2 && terms3 && terms4) {
            // 모든 약관에 동의했으면 회원가입 페이지로 리디렉션
            return "redirect:/member/registerSeller"; // 회원가입 페이지로 이동
        } else {
            // 약관 동의하지 않으면 경고 메시지나 다시 약관 페이지로 리디렉션
            return "redirect:/member/signUp_seller"; // 약관 동의 페이지로 다시 이동
        }
    }


    //회원 - 판매자가입
    @GetMapping("/registerSeller")
    public String registerSeller() {
        return "/member/registerSeller";
    }

    //판매자 회원가입처리
    @PostMapping("/registerSeller")
    public String registerSellerPost(@ModelAttribute SellerDTO sellerDTO){
        sellerService.register(sellerDTO);
        return "redirect:/member/login";
    }

    //판매자 아이디 중복 체크
    @GetMapping("/check-id/{sellerId}")
    public ResponseEntity<Map<String, Boolean>> checkSellerId(@PathVariable String sellerId){
        Map<String, Boolean> response = new HashMap<>();
        boolean exists= sellerService.existsBySellerId(sellerId);
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }



    /* **************************회원 끝*********************************** */
}
