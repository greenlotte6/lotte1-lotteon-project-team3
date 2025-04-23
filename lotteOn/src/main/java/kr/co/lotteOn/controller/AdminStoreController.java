package kr.co.lotteOn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminStoreController {

    /*------------ 관리자 - 상점관리 ------------*/

    //상점관리 - 상점목록
    @GetMapping("/shop/list")
    public String shopList(){
        return "/admin/shop/list";
    }

    //상점 등록 처리


    //상점관리 - 매출관리
    @GetMapping("/shop/sales")
    public String shopSales(){
        return "/admin/shop/sales";
    }


}