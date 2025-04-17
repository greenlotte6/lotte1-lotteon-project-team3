package kr.co.lotteOn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/intro")
@Controller
public class IntroController {

    //회사소개 - 메인
    @GetMapping("/intro_home")
    public String home(){
        return "intro/intro_home";
    }
    //회사소개 기업문화
    @GetMapping("/intro_culture")
    public String culture(){
        return "intro/intro_culture";
    }
    //회사소개 - 채용
    @GetMapping("/intro_employ")
    public String employ(){
        return "intro/intro_employ";
    }
    //회사소개 - 미디어
    @GetMapping("/intro_media")
    public String media(){
        return "intro/intro_media";
    }
    //회사소개 - 소식과 이야기
    @GetMapping("/intro_story")
    public String story(){
        return "intro/intro_story";
    }

    /* **************************회사소개 끝*********************************** */
}
