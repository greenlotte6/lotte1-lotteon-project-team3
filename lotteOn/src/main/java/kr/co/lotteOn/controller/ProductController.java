package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.ProductDTO;
import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Product;
import kr.co.lotteOn.security.MyUserDetails;
import kr.co.lotteOn.service.IssuedCouponService;
import kr.co.lotteOn.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/product")
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final IssuedCouponService issuedCouponService;

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
        log.warn("🔥🔥🔥 paymentPage() 컨트롤러 진입");
        if (myUserDetails == null) {
            return "redirect:/member/login?redirect=/product/detail?productCode=" + productCode;
        }
        Member member = myUserDetails.getMember();
        ProductDTO product = productService.getProductByCode(productCode);

        List<IssuedCouponDTO> coupons = issuedCouponService.getAvailableCouponsForMember(member.getId());
        model.addAttribute("coupons", coupons);

        log.info("🔥 POST payment: memberId={}", member.getId());
        log.info("🔥 POST payment: coupon size = {}", coupons.size());
        coupons.forEach(c -> log.info("🔥 쿠폰: {}", c));

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
    public String payment(@AuthenticationPrincipal MyUserDetails myUserDetails,
                          @RequestParam String productCode,
                          @RequestParam int quantity,
                          @RequestParam String option,
                          Model model) {
        String memberId = myUserDetails.getMember().getId();
        List<IssuedCouponDTO> coupons = issuedCouponService.getAvailableCouponsForMember(memberId);
        log.info("coupons: {}", coupons);

        model.addAttribute("coupons", coupons);
        model.addAttribute("productCode", productCode);
        model.addAttribute("quantity", quantity);
        model.addAttribute("option", option);
        model.addAttribute("memberId", memberId);

        return "/product/payment";
    }
    @GetMapping("/payment/coupons")
    @ResponseBody
    public List<IssuedCouponDTO> getCoupons(@AuthenticationPrincipal MyUserDetails myUserDetails) {
        String memberId = myUserDetails.getMember().getId();
        return issuedCouponService.getAvailableCouponsForMember(memberId);
    }

    //상품 - 주문완료
    @GetMapping("/completeOrder")
    public String completeOrder(){
        return "/product/completeOrder";
    }

    //상품 - 검색


}
