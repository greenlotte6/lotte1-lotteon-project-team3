package kr.co.lotteOn.service;

import jakarta.mail.Session;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final Map<String, String> emailAuthMap = new ConcurrentHashMap<>();


    public String sendEmail(String to, HttpSession session) {
        String authCode = String.valueOf((int)(Math.random() * 900000) + 100000);
        String key = "authCode:" + to.toLowerCase();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("(주)롯데온 이메일 인증코드");
        message.setText("인증코드 : " + authCode);
        mailSender.send(message);

        session.setAttribute(key, authCode);
        log.info("이메일 발송: {}, 코드: {}, 세션키: {}", to, authCode, key);
        log.info("세션 ID (발송 시): {}", session.getId());
        System.out.println(to);
        System.out.println(authCode);
        System.out.println(key);
        System.out.println(session.getId());
        return authCode;
    }

    public boolean verifyCode(String code, String email, HttpSession session) {
        String key = "authCode:" + email.toLowerCase();
        String savedCode = (String) session.getAttribute(key);
        log.info("인증 요청 - 입력 코드: {}, 저장 코드: {}, 키: {}", code, savedCode, key);
        log.info("세션 ID (검증 시): {}", session.getId());
        System.out.println(code);
        System.out.println(savedCode);
        System.out.println(key);
        return savedCode != null && savedCode.equals(code);
    }

}
