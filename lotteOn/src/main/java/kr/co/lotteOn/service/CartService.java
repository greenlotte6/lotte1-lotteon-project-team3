package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.CartDTO;
import kr.co.lotteOn.entity.Cart;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Product;
import kr.co.lotteOn.repository.CartRepository;
import kr.co.lotteOn.repository.MemberRepository;
import kr.co.lotteOn.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public void addToCart(String memberId, Long productId, int quantity, String productOption) {
        Product product = productRepository.findById(productId).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();

        Cart cart = cartRepository.findByMember_IdAndProduct_Id(memberId, productId)
                .orElse(new Cart());
        cart.setMember(member);
        cart.setProduct(product);
        cart.setQuantity(cart.getQuantity() + quantity);
        cart.setAddedDate(LocalDateTime.now());
        cart.setProductOption(productOption);
        cartRepository.save(cart);
    }
    public List<CartDTO> getCartByMember(String memberId) {
        return cartRepository.findWithProductByMemberId(memberId).stream().map(cart -> {
            Product p = cart.getProduct();

            //옵션값 추출
            String rawOption = cart.getProductOption();
            String optionValues = "";
            if (rawOption != null && !rawOption.isEmpty()) {
                optionValues = Arrays.stream(rawOption.split(","))
                                        .map(s -> s.split(":")[1].trim())
                                        .collect(Collectors.joining("<br>"));
            }

            CartDTO dto = new CartDTO();
            dto.setId(cart.getId());
            dto.setProductId(p.getId());
            dto.setProductName(p.getName());
            dto.setProductImage(p.getImageList());
            dto.setQuantity(cart.getQuantity());
            dto.setPrice(p.getPrice());
            dto.setDiscount(p.getDiscount());
            dto.setDeliveryFee(p.getDeliveryFee());
            dto.setProductOption(cart.getProductOption());
            dto.setProductCode(p.getProductCode());
            return dto;
        }).toList();
    }

    @Transactional
    public CartDTO updateQuantityAndGetCart(Long cartId, int quantity) {
        Cart cart = cartRepository.findWithProductById(cartId)
                .orElseThrow(() -> new RuntimeException("장바구니 항목을 찾을 수 없습니다."));

        cart.setQuantity(quantity);

        Product p = cart.getProduct();

        return CartDTO.builder()
                .id(cart.getId())
                .productId(p.getId())
                .productName(p.getName())
                .productImage(p.getImageList())
                .quantity(cart.getQuantity())
                .price(p.getPrice())
                .discount(p.getDiscount())
                .deliveryFee(p.getDeliveryFee())
                .productCode(p.getProductCode())
                .build();
    }

    public void deleteCartItem(Long cartId) {
        cartRepository.deleteById(cartId);
    }
    public void deleteCartItems(List<Long> cartIds) {
        cartRepository.deleteAllById(cartIds);
    }
}
