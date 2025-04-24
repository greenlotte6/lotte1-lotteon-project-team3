package kr.co.lotteOn.controller;

import jakarta.servlet.http.HttpSession;
import kr.co.lotteOn.service.EmailService;
import kr.co.lotteOn.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {

    private final EmailService emailService;
    private final MemberService memberService;

    @GetMapping("/check-member-id/{id}")
    public Map<String, Boolean> checkId(@PathVariable String id) {
        boolean exists = memberService.isIdExist(id);
        return Map.of("exists", exists);
    }

    @PostMapping("/email/send")
    public ResponseEntity<String> sendAuthCode(@RequestParam String email, HttpSession session) {
        String code = emailService.sendEmail(email, session);
        return ResponseEntity.ok(code);
    }

    @PostMapping("/email/verify")
    public ResponseEntity<Boolean> verifyCode(@RequestParam String code, @RequestParam String email, HttpSession session) {
        boolean result = emailService.verifyCode(code, email, session);
        return ResponseEntity.ok(result);
    }

}
