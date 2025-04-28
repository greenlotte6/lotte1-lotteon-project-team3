package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.ProductDTO;
import kr.co.lotteOn.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/product")
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //상품 - 목록
    @GetMapping("/list")
    public String productList(@RequestParam(name = "categoryId", required = false) Long categoryId, Model model) {
        List<ProductDTO> products;
        if (categoryId != null) {
            products = productService.getProductsByCategoryId(categoryId);
        }else {
            products = productService.getAllProducts();
        }
        model.addAttribute("products", products);
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
