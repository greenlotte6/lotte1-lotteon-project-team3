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

    /*------------ 관리자 - 상점관리 ------------*/

    //환경설정 - 기본설정
    @GetMapping("/config/basic")
    public String basic(){
        return "/admin/config/basic";
    }

    //환경설정 - 배너관리
    @GetMapping("/config/banner")
    public String banner(){
        return "/admin/config/banner";
    }
    @GetMapping("/config/bannerLogin")
    public String bannerLogin(){
        return "/admin/config/bannerLogin";
    }
    @GetMapping("/config/bannerMainsl")
    public String bannerMainsl(){
        return "/admin/config/bannerMainsl";
    }
    @GetMapping("/config/bannerMypage")
    public String bannerMypage(){
        return "/admin/config/bannerMypage";
    }
    @GetMapping("/config/bannerProduct")
    public String bannerProduct(){
        return "/admin/config/bannerProduct";
    }

    //환경설정 - 약관관리
    @GetMapping("/config/policy")
    public String policy(){
        return "/admin/config/policy";
    }

    //환경설정 - 버전관리
    @GetMapping("/config/version")
    public String version(){
        return "/admin/config/version";
    }

    //환경설정 - 카테고리 관리
    @GetMapping("/config/category")
    public String category(){
        return "/admin/config/category";
    }

    /*------------ 관리자 - 상점관리 ------------*/

    //상점관리 - 상점목록
    @GetMapping("/shop/list")
    public String shopList(){
        return "/admin/config/list";
    }

    //상점관리 - 매출관리
    @GetMapping("/shop/sales")
    public String shopSales(){
        return "/admin/config/sales";
    }


    /*------------ 관리자 - 주문관리 ------------*/

    //주문관리 - 목록
    @GetMapping("/order/list")
    public String orderList(){
        return "/admin/order/list";
    }

    //주문관리 - 주문현황
    @GetMapping("/order/delivery")
    public String orderDelivery(){
        return "/admin/order/delivery";
    }


    /*------------ 관리자 - 상품관리 ------------*/

    //상품관리 - 목록
    @GetMapping("/product/list")
    public String productList(){
        return "/admin/product/list";
    }


    /*------------ 관리자 - 고객관리 ------------*/

    //고객관리 - 목록
    @GetMapping("/member/list")
    public String memberList(){
        return "/admin/member/list";
    }

    //고객관리 - 포인트현황
    @GetMapping("/member/point")
    public String memberPoint(){
        return "/admin/member/point";
    }


    /*------------ 관리자 - 쿠폰관리 ------------*/

    //쿠폰관리 - 목록
    @GetMapping("/coupon/list")
    public String couponList(){
        return "/admin/coupon/list";
    }

    //쿠폰관리 - 쿠폰현황
    @GetMapping("/coupon/issued")
    public String couponIssued(){
        return "/admin/coupon/issued";
    }


    /*------------ 관리자 - 고객센터 ------------*/

    //고객센터 - 공지사항
    @GetMapping("/cs/noticeList")
    public String noticeList(){
        return "/admin/cs/noticeList";
    }

    //고객센터 - 자주묻는질문
    @GetMapping("/cs/faqList")
    public String faqList(){
        return "/admin/cs/faqList";
    }

    //고객센터 - 문의하기
    @GetMapping("/cs/qnaList")
    public String qnaList(){
        return "/admin/cs/qnaList";
    }

    //고객센터 - 채용정보
    @GetMapping("/cs/recruitList")
    public String recruitList(){
        return "/admin/cs/recruitList";
    }


}
