package kr.co.lotteOn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminOrderController {

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

}
