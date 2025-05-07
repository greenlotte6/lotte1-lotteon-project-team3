package kr.co.lotteOn.controller;

import jakarta.validation.Valid;
import kr.co.lotteOn.dto.*;
import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponDTO;
import kr.co.lotteOn.entity.*;
import kr.co.lotteOn.security.MyUserDetails;
import kr.co.lotteOn.service.*;
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
    private final PointService pointService;
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    private void preparePaymentPage(Member member, ProductDTO product, int quantity, String option, Model model) {
        // 1. 상품 계산
        int originalTotal = product.getPrice() * quantity;
        int discountedTotal = originalTotal * (100 - product.getDiscount()) / 100;
        int discountAmount = originalTotal - discountedTotal;
        int deliveryFee = discountedTotal >= 30000 ? 0 : 3000;
        int finalTotal = discountedTotal + deliveryFee;
        int discountedPrice = product.getPrice() * (100 - product.getDiscount()) / 100 * quantity;

        // 2. 쿠폰 및 포인트
        List<IssuedCouponDTO> coupons = issuedCouponService.getAvailableCouponsForMember(member.getId());
        Point latestPoint = pointService.getLatestPoint(member);
        int memberPoint = (latestPoint != null) ? latestPoint.getTotalPoint() : 0;

        // 3. 상품 정보
        Map<String, Object> item = new HashMap<>();
        item.put("product", product);
        item.put("quantity", quantity);
        item.put("option", option);

        // 4. model에 담기
        model.addAttribute("items", List.of(item));
        model.addAttribute("coupons", coupons);
        model.addAttribute("member", member);
        model.addAttribute("memberId", member.getId());
        model.addAttribute("productCode", product.getProductCode());
        model.addAttribute("quantity", quantity);
        model.addAttribute("option", option);

        model.addAttribute("originalTotal", originalTotal);
        model.addAttribute("discountedTotal", discountedTotal);
        model.addAttribute("discountAmount", discountAmount);
        model.addAttribute("deliveryFee", deliveryFee);
        model.addAttribute("finalTotal", finalTotal);
        model.addAttribute("memberPoint", memberPoint);
        model.addAttribute("discountedPrice", discountedPrice);
    }

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

    @GetMapping("/payment")
    public String paymentGet(@AuthenticationPrincipal MyUserDetails myUserDetails,
                             @RequestParam String productCode,
                             @RequestParam int quantity,
                             @RequestParam String option,
                             Model model) {

        Member member = myUserDetails.getMember();
        ProductDTO product = productService.getProductByCode(productCode);
        preparePaymentPage(member, product, quantity, option, model);
        return "/product/payment";
    }

    @PostMapping("/payment")
    public String paymentPost(@AuthenticationPrincipal MyUserDetails myUserDetails,
                              @RequestParam String productCode,
                              @RequestParam int quantity,
                              @RequestParam String option,
                              Model model) {

        if (myUserDetails == null) {
            return "redirect:/member/login?redirect=/product/detail?productCode=" + productCode;
        }

        Member member = myUserDetails.getMember();
        ProductDTO product = productService.getProductByCode(productCode);
        preparePaymentPage(member, product, quantity, option, model);
        return "/product/payment";
    }
    @GetMapping("/payment/coupons")
    @ResponseBody
    public List<IssuedCouponDTO> getCoupons(@AuthenticationPrincipal MyUserDetails myUserDetails) {
        String memberId = myUserDetails.getMember().getId();
        return issuedCouponService.getAvailableCouponsForMember(memberId);
    }

    //상품 - 주문완료
    @GetMapping("/completeOrder/{orderCode}")
    public String showCompleteOrder(@PathVariable String orderCode, Model model) {
        Order order = orderService.getOrderByCode(orderCode);
        List<OrderItem> items = orderItemService.getItemsByOrderCode(orderCode);

        OrderResultDTO dto = OrderResultDTO.fromEntity(order, items.get(0));

        model.addAttribute("order", order);
        model.addAttribute("items", items);
        return "/product/completeOrder";
    }

    @PostMapping("/completeOrder")
    public String completeOrder(@ModelAttribute OrderRequestDTO orderRequestDTO,
                                @ModelAttribute OrderItemListDTO itemListDTO,
                                Model model) {
        String orderCode = orderService.createOrder(orderRequestDTO, itemListDTO.getItems());

        orderItemService.saveOrderItems(orderCode, itemListDTO.getItems());

        Order order = orderService.getOrderByCode(orderCode);
        List<OrderItem> items = orderItemService.getItemsByOrderCode(orderCode);

        model.addAttribute("order", order);
        model.addAttribute("items", items);
        return "redirect:/product/completeOrder/" + orderCode;

    }

}
