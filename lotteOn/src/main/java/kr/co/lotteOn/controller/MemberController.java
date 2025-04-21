package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.SellerDTO;
import kr.co.lotteOn.service.SellerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/member")
@Controller
public class MemberController {

    private final SellerService sellerService;

    public MemberController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

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
