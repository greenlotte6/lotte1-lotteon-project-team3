package kr.co.lotteOn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminLotteController {

    //관리자 - 메인
    @GetMapping("/admin")
    public String admin(){
        return "/admin/admin";
    }


    /*------------ 관리자 - 상품관리 ------------*/

    //상품관리 - 목록
    @GetMapping("/product/list")
    public String productList(){
        return "/admin/product/list";
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



}
