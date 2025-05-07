package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.OrderItemDTO;
import kr.co.lotteOn.dto.OrderItemListDTO;
import kr.co.lotteOn.dto.OrderRequestDTO;
import kr.co.lotteOn.dto.OrderResultDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Order;
import kr.co.lotteOn.entity.Product;
import kr.co.lotteOn.repository.MemberRepository;
import kr.co.lotteOn.repository.OrderRepository;
import kr.co.lotteOn.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public String createOrder(OrderRequestDTO dto, List<OrderItemDTO> items) {
        // 1. Member, Product 조회
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        // 2. 주문코드 생성 (UUID or 조합된 규칙 기반)
        String orderCode = UUID.randomUUID().toString();

        // 3. Order 생성 및 저장
        Order order = Order.builder()
                .orderCode(orderCode)
                .member(member)
                .name(dto.getName())
                .payment(dto.getPayment())
                .orderStatus("결제완료")
                .orderDate(LocalDateTime.now())
                .delivery(dto.getDelivery())
                .discount(dto.getDiscount())
                .fee(dto.getFee())
                .actualMoney(dto.getActualMoney())
                .build();

        orderRepository.save(order);

        for (OrderItemDTO itemDTO : items) {
            productRepository.findByProductCode(itemDTO.getProductCode())
                    .orElseThrow(() -> new IllegalArgumentException("상품 없음"));
        }
        return orderCode; // 이후 조회용으로 return
    }

    public Order getOrderByCode(String orderCode) {
        return orderRepository.findById(orderCode)
                .orElseThrow(() -> new IllegalArgumentException("주문 정보 없음"));
    }
}
