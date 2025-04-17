package kr.co.lotteOn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/product")
@Controller
public class ProductController {

    //상품 - 목록?
    @GetMapping("/list")
    public String list(){
        return "/product/list";
    }

    //상품 - 상세보기

    //상품 - 장바구니

    //상품 - 주문하기

    //상품 - 주문완료

    //상품 - 검색


}
