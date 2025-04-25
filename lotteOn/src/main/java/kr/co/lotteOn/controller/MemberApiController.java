package kr.co.lotteOn.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import kr.co.lotteOn.dto.MemberDTO;
import kr.co.lotteOn.service.EmailService;
import kr.co.lotteOn.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberApiController {

    private final EmailService emailService;
    private final MemberService memberService;

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
        return ResponseEntity.ok(result);
    }
}
