package kr.co.lotteOn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/product")
@Controller
public class ProductController {

    //상품 - 목록
    @GetMapping("/list")
    public String list(){
        return "/product/list";
    }

    //상품 - 상세보기
    @GetMapping("/detail")
    public String detail(){
        return "/product/detail";
    }

    //상품 - 장바구니
    @GetMapping("/cart")
    public String cart(){
        return "/product/cart";
    }

    //상품 - 주문하기
    @GetMapping("/payment")
    public String payment(){
        return "/product/payment";
    }

    //상품 - 주문완료
    @GetMapping("/completeOrder")
    public String completeOrder(){
        return "/product/completeOrder";
    }

    //상품 - 검색


}
