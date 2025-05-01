package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.ProductDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Product;
import kr.co.lotteOn.security.MyUserDetails;
import kr.co.lotteOn.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String detail(@RequestParam String productCode, Model model) {
        ProductDTO product = productService.getProductByCode(productCode);
        if (product == null) {
            return "redirect:/product/list";
        }
        model.addAttribute("product", product);
        return "/product/detail";
    }

    @PostMapping("/payment")
    public String paymentPage(@AuthenticationPrincipal MyUserDetails myUserDetails,
                            @RequestParam String productCode,
                            @RequestParam int quantity,
                            @RequestParam String option,
                            Model model) {
        if (myUserDetails == null) {
            return "redirect:/member/login?redirect=/product/detail?productCode=" + productCode;
        }
        Member member = myUserDetails.getMember();
        ProductDTO product = productService.getProductByCode(productCode);

        Map<String, Object> item = new HashMap<>();
        item.put("product", product);
        item.put("quantity", quantity);
        item.put("option", option);

        model.addAttribute("items", List.of(item));
        model.addAttribute("member", member);

        return "/product/payment";
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
