package kr.co.lotteOn.controller;

import jakarta.validation.Valid;
import kr.co.lotteOn.dto.SellerDTO;
import kr.co.lotteOn.dto.ShopDTO;
import kr.co.lotteOn.dto.TermsDTO;
import kr.co.lotteOn.service.SellerService;
import kr.co.lotteOn.service.ShopService;
import kr.co.lotteOn.service.TermsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import kr.co.lotteOn.dto.MemberDTO;
import kr.co.lotteOn.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final ShopService shopService;

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
    public String register(@Valid @ModelAttribute MemberDTO memberDTO,
                           BindingResult result) {
        if (result.hasErrors()) {
            return "/member/register";
        }
        memberService.register(memberDTO);
        return "redirect:/member/login";
    }

    //회원 - 아이디 찾기
    @GetMapping("/findId")
    public String findId() {
        return "/member/findId";
    }

    //회원 - 아이디 찾기 결과
    @GetMapping("/resultId")
    public String resultId() {
        return "/member/resultId";
    }
    
    //회원 - 비밀번호 찾기 결과
    @GetMapping("/resultPass")
    public String resultPass() {
        return "/member/resultPass";
    }

    //회원 - 휴대폰 존재 여부
    @GetMapping("/checkHp/{hp}")
    public ResponseEntity<Map<String, Boolean>> checkHp(@PathVariable String hp) {
        Map<String, Boolean> response = new HashMap<>();
        boolean exists = memberService.existsByHp(hp);
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    //회원 - 이메일 존재 여부
    @GetMapping("/checkEmail/{email}")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@PathVariable String email) {
        Map<String, Boolean> response = new HashMap<>();
        boolean exists = memberService.existsByEmail(email);
        response.put("exists", exists);
        return ResponseEntity.ok(response);
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

    @GetMapping("/checkCompanyName/{companyName}")
    public ResponseEntity<Map<String, Boolean>> checkCompanyName(@PathVariable String companyName) {
        Map<String, Boolean> response = new HashMap<>();
        boolean exists = sellerService.isCompanyNameExist(companyName);
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/checkBusinessNo/{businessNo}")
    public ResponseEntity<Map<String, Boolean>> checkBusinessNo(@PathVariable String businessNo) {
        Map<String, Boolean> response = new HashMap<>();
        boolean exists = sellerService.isBusinessNoExist(businessNo);
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/checkCommunicationNo/{communicationNo}")
    public ResponseEntity<Map<String, Boolean>> checkCommunicationNo(@PathVariable String communicationNo) {
        Map<String, Boolean> response = new HashMap<>();
        boolean exists = sellerService.isCommunicationNoExist(communicationNo);
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/checkSHp/{hp}")
    public ResponseEntity<Map<String, Boolean>> checkSHp(@PathVariable String hp) {
        Map<String, Boolean> response = new HashMap<>();
        boolean exists = sellerService.isSHpExist(hp);
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/checkFax/{fax}")
    public ResponseEntity<Map<String, Boolean>> checkFax(@PathVariable String fax) {
        Map<String, Boolean> response = new HashMap<>();
        boolean exists = sellerService.isFaxExist(fax);
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    /* **************************회원 끝*********************************** */
}
