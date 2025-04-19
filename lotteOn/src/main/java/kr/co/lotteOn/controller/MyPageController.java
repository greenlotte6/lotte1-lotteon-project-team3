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
    @GetMapping("/my_order")
    public String myOrder() {
        return "/myPage/my_order";
    }
    //마이페이지 - 포인트내역
    @GetMapping("/my_point")
    public String myPoint() {
        return "/myPage/my_point";
    }

    //마이페이지 - 쿠폰
    @GetMapping("/my_coupon")
    public String myCoupon() {
        return "/myPage/my_coupon";
    }

    //마이페이지 - 나의리뷰
    @GetMapping("/my_review")
    public String myReview() {
        return "/myPage/my_review";
    }

    //마이페이지 - 문의하기
    @GetMapping("/my_qna")
    public String myQna() {
        return "/myPage/my_qna";
    }
    //마이페이지 - 나의설정
    @GetMapping("/my_info")
    public String myInfo() {
        return "/myPage/my_info";
    }
}
