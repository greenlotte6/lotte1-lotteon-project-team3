package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.OrderItemDTO;
import kr.co.lotteOn.dto.OrderItemListDTO;
import kr.co.lotteOn.dto.OrderRequestDTO;
import kr.co.lotteOn.dto.OrderResultDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Order;
import kr.co.lotteOn.entity.OrderItem;
import kr.co.lotteOn.entity.Product;
import kr.co.lotteOn.repository.MemberRepository;
import kr.co.lotteOn.repository.OrderRepository;
import kr.co.lotteOn.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final OrderItemService orderItemService;

    public String createOrder(OrderRequestDTO dto, List<OrderItemDTO> items) {
        // 1. Member, Product 조회
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        // 2. 주문코드 생성 (UUID or 조합된 규칙 기반)
        String orderCode = generateOrderCode();

        // 3. Order 생성 및 저장
        Order order = Order.builder()
                .orderCode(orderCode)
                .member(member)
                .receiver(dto.getReceiver())
                .name(dto.getName())
                .delivery(dto.getDelivery())
                .payment(dto.getPayment())
                .discount(dto.getDiscount())
                .fee(dto.getFee())
                .actualMoney(dto.getActualMoney())
                .orderStatus("결제완료")
                .orderDate(LocalDateTime.now())
                .build();

        for (OrderItemDTO itemDTO : items) {
            Product product = productRepository.findByProductCode(itemDTO.getProductCode())
                    .orElseThrow(() -> new IllegalArgumentException("상품 없음"));

            OrderItem item = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemDTO.getQuantity())
                    .price(itemDTO.getPrice())
                    .discount(itemDTO.getDiscount())
                    .total(itemDTO.getTotal())
                    .build();
            order.getItems().add(item);
        }
        orderRepository.save(order);
        return orderCode; // 이후 조회용으로 return
    }

    public Order getOrderByCode(String orderCode) {
        return orderRepository.findById(orderCode)
                .orElseThrow(() -> new IllegalArgumentException("주문 정보 없음"));
    }

    public String generateOrderCode(){
        String prefix = "ORD";
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int random = (int)(Math.random() * 9000) + 1000;

        return prefix + "-" + date + "-" + random;
    }

}
