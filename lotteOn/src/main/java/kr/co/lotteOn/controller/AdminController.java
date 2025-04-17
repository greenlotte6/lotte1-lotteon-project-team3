package kr.co.lotteOn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminController {

    //관리자 - 메인
    @GetMapping("/admin")
    public String admin(){
        return "/admin/admin";
    }

    //환경설정 - 기본설정

    //환경설정 - 배너관리

    //환경설정 - 약관관리

    //환경설정 - 버전관리

}
