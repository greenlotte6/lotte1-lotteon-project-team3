package kr.co.lotteOn.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kr.co.lotteOn.dto.*;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.service.SellerService;
import kr.co.lotteOn.service.ShopService;
import kr.co.lotteOn.service.TermsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import kr.co.lotteOn.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final SellerService sellerService;
    private final MemberService memberService;
    private final TermsService termsService;
    private final ShopService shopService;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession httpSession;

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
                           BindingResult result,
                           @RequestParam String rrnFront,
                           @RequestParam String rrnBack) {

        if (result.hasErrors()) {
            return "/member/register";
        }
        
        if (rrnFront.length() == 6 && rrnBack.length() == 1) {
            String genderDigit = rrnBack;

            int yearPrefix = (genderDigit.equals("1") || genderDigit.equals("2")) ? 1900 :
                    (genderDigit.equals("3") || genderDigit.equals("4")) ? 2000 : -1;

            if (yearPrefix != -1) {
                String birthYear = String.valueOf(yearPrefix + Integer.parseInt(rrnFront.substring(0, 2)));
                String birthMonth = rrnFront.substring(2, 4);
                String birthDay = rrnFront.substring(4, 6);


                LocalDate birthDate = LocalDate.parse(birthYear + "-" + birthMonth + "-" + birthDay);
                String gender = (genderDigit.equals("1") || genderDigit.equals("3")) ? "남" : "여";

                memberDTO.setBirthDate(birthDate);
                memberDTO.setGender(gender);

            } else {
                result.rejectValue("gender", null, "유효하지 않은 주민등록번호입니다.");
                return "/member/register";
            }
        } else {
            result.rejectValue("gender", null, "주민등록번호 형식이 잘못되었습니다.");
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
    public String resultId(@ModelAttribute("member") Member member, Model model) {
        if (member == null) {
            return "redirect:/error";
        }

        model.addAttribute("member", member); // 선택
        return "/member/resultId";
    }
    
    //회원 - 비밀번호 찾기 결과
    @GetMapping("/resultPass")
    public String resultPass(@ModelAttribute("member") Member member, Model model) {
        if (member == null) {
            return "redirect:/error";
        }

        model.addAttribute("member", member); // 선택
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

    @PostMapping("/resultId")
    public String showResultId(HttpSession session, RedirectAttributes redirectAttributes) {
        String email = (String) session.getAttribute("verifiedEmail");
        if (email == null) {
            return "redirect:/error";
        }

        Optional<Member> member = memberService.findByEmail(email);
        redirectAttributes.addFlashAttribute("member", member.orElse(null));
        return "redirect:/member/resultId";
    }

    @PostMapping("/resultPass")
    public String showResultPass(HttpSession session, RedirectAttributes redirectAttributes) {
        String email = (String) session.getAttribute("verifiedEmail");
        if (email == null) {
            return "redirect:/error";
        }

        Optional<Member> member = memberService.findByEmail(email);
        redirectAttributes.addFlashAttribute("member", member.orElse(null));
        return "redirect:/member/resultPass";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("password") String password,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 @RequestParam("id") String id,
                                 Model model) {

        // 비밀번호 확인
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "/member/resultPass"; // 비밀번호 불일치 시 다시 결과 페이지로
        }

        // 비밀번호 암호화 처리
        String encryptedPassword = passwordEncoder.encode(password); // 비밀번호 암호화

        Optional<Member> memberOpt = memberService.findById(id);
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            member.setPassword(encryptedPassword);
            memberService.save(member); // 비밀번호 변경된 회원 저장

            model.addAttribute("message", "비밀번호가 성공적으로 변경되었습니다.");
            return "redirect:/member/login"; // 비밀번호 변경 후 로그인 페이지로 리디렉션
        } else {
            model.addAttribute("error", "회원 정보를 찾을 수 없습니다.");
            return "/member/resultPass"; // 회원 정보가 없으면 다시 결과 페이지로
        }
    }


    /* **************************회원 끝*********************************** */

}