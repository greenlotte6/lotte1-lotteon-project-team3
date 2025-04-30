package kr.co.lotteOn.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import kr.co.lotteOn.dto.MemberDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.repository.MemberRepository;
import kr.co.lotteOn.service.EmailService;
import kr.co.lotteOn.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberApiController {

    private final EmailService emailService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/check-member-id/{id}")
    public Map<String, Boolean> checkId(@PathVariable String id) {
        boolean exists = memberService.isIdExist(id);
        return Map.of("exists", exists);
    }

    @PostMapping("/email/send")
    public ResponseEntity<String> sendAuthCode(@RequestParam @Email String email, HttpSession session) {
        String code = emailService.sendEmail(email, session);
        return ResponseEntity.ok("인증코드가 이메일로 전송되었습니다.");
    }

    @PostMapping("/email/verify")
    public ResponseEntity<Boolean> verifyCode(@RequestParam String code,
                                              @RequestParam String email,
                                              HttpSession session) {
        boolean result = emailService.verifyCode(code, email, session);
        if (result) {
            session.setAttribute("verifiedEmail", email);  // 세션에 저장
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/check-name-email")
    public ResponseEntity<Map<String, Boolean>> checkNameEmail(@RequestBody Map<String, String> payload) {
        String name = payload.get("name");
        String email = payload.get("email");

        boolean isValid = memberRepository.existsByNameAndEmail(name, email);
        return ResponseEntity.ok(Collections.singletonMap("valid", isValid));
    }

    @PostMapping("/check-id-email")
    public ResponseEntity<Map<String, Boolean>> checkIdEmail(@RequestBody Map<String, String> payload) {
        String id = payload.get("id");
        String email = payload.get("email");

        boolean isValid = memberRepository.existsByIdAndEmail(id, email);
        return ResponseEntity.ok(Collections.singletonMap("valid", isValid));
    }
}
