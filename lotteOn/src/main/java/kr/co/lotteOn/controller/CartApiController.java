package kr.co.lotteOn.controller;

import kr.co.lotteOn.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product/cart")
public class CartApiController {

    private final CartService cartService;

    @DeleteMapping("/delete/{cartId}")
    @ResponseBody
    public void deleteCartItem(@PathVariable Long cartId) {
        cartService.deleteCartItem(cartId);
    }
    @PostMapping("/deleteSelected")
    @ResponseBody
    public void deleteSelectedCartItems(@RequestBody List<Long> cartIds) {
        cartService.deleteCartItems(cartIds);
    }


}
