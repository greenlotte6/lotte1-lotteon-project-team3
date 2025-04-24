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

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("(주)롯데온 이메일 인증코드");
        message.setText("인증코드 : " + authCode);
        mailSender.send(message);

        session.setAttribute("authCode:" + to, authCode);
        return authCode;
    }

    public boolean verifyCode(String email, String inputCode, HttpSession session) {
        String savedCode = (String) session.getAttribute("authCode:" + email);
        return savedCode != null && savedCode.equals(inputCode);
    }

}
