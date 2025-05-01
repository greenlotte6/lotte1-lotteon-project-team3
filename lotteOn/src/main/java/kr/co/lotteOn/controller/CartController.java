package kr.co.lotteOn.controller;

import jakarta.servlet.http.HttpSession;
import kr.co.lotteOn.dto.CartDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.security.MyUserDetails;
import kr.co.lotteOn.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/product")
public class CartController {

    private final CartService cartService;

    @PostMapping("/addToCart")
    public String addToCart(@AuthenticationPrincipal MyUserDetails myUserDetails,
                            @RequestParam Long productId,
                            @RequestParam int quantity,
                            @RequestParam String productOption,
                            RedirectAttributes redirectAttributes) {
        if (myUserDetails == null) {
            return "redirect:/member/login?redirect=/product/detail?productId=" + productId;
        }

        String memberId = myUserDetails.getMember().getId();
        cartService.addToCart(memberId, productId, quantity, productOption);
        return "redirect:/product/cart";
    }


    @GetMapping("/cart")
    public String cartPage(@AuthenticationPrincipal MyUserDetails myUserDetails,
                           Model model) {

        if (myUserDetails == null) {
            System.out.println("로그인 안된 상태");
            return "redirect:/member/login?redirect=/product/cart";
        }

        String memberId = myUserDetails.getMember().getId();
        System.out.println("로그인된 Id" + memberId);
        List<CartDTO> cartList = cartService.getCartByMember(memberId);

        String fullAddress = myUserDetails.getMember().getAddr1() + " " + myUserDetails.getMember().getAddr2();
        model.addAttribute("fullAddress", fullAddress);

        int totalProductPrice = 0;
        int totalDiscount = 0;
        int deliveryFee = cartList.stream()
                                    .mapToInt(CartDTO::getDeliveryFee)
                                    .sum();

        for (CartDTO cart : cartList) {
            int itemPrice = cart.getPrice() * cart.getQuantity();
            int discountAmount = (cart.getPrice() * cart.getDiscount() / 100) * cart.getQuantity();
            totalProductPrice += itemPrice;
            totalDiscount += discountAmount;
        }

        int totalOrderPrice = totalProductPrice - totalDiscount + deliveryFee;

        model.addAttribute("cartList", cartList);
        model.addAttribute("totalProductPrice", totalProductPrice);
        model.addAttribute("totalDiscount", totalDiscount);
        model.addAttribute("deliveryFee", deliveryFee);
        model.addAttribute("totalOrderPrice", totalOrderPrice);


        return "/product/cart";
    }

    @PostMapping("/cart/updateQuantity")
    @ResponseBody
    public Map<String, Object> updateCartQuantity(@RequestBody Map<String, Object> payload) {

        Long cartId = Long.valueOf(payload.get("cartId").toString());
        int quantity = Integer.parseInt(payload.get("quantity").toString());

        CartDTO updated = cartService.updateQuantityAndGetCart(cartId, quantity);

        int discountPrice = updated.getPrice() * (100 - updated.getDiscount()) / 100;
        int productTotalPrice = discountPrice * updated.getQuantity();

        return Map.of(
                "updatedQuantity", updated.getQuantity(),
                "productTotalPrice", productTotalPrice,
                "price", updated.getPrice(),
                "discount", updated.getDiscount(),
                "deliveryFee", updated.getDeliveryFee()
        );
    }


}
