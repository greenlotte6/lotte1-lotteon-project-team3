package kr.co.lotteOn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/myPage")
@Controller
public class MyPageController {

    //마이페이지 - 메인
    @GetMapping("/my_home")
    public String myHome() {
        return "/myPage/my_home";
    }

    //마이페이지 - 전체주문내역

    //마이페이지 - 포인트내역

    //마이페이지 - 쿠폰

    //마이페이지 - 나의리뷰

    //마이페이지 - 문의하기

    //마이페이지 - 나의설정

}
