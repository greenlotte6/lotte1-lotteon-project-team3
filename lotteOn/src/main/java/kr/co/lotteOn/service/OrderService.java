package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.OrderItemDTO;
import kr.co.lotteOn.dto.OrderItemListDTO;
import kr.co.lotteOn.dto.OrderRequestDTO;
import kr.co.lotteOn.dto.OrderResultDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Order;
import kr.co.lotteOn.entity.OrderItem;
import kr.co.lotteOn.entity.Product;
import kr.co.lotteOn.repository.*;
import kr.co.lotteOn.repository.CartRepository;
import kr.co.lotteOn.repository.MemberRepository;
import kr.co.lotteOn.repository.OrderRepository;
import kr.co.lotteOn.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final OrderItemService orderItemService;
    private final RefundRepository refundRepository;
    private final QnaRepository qnaRepository;
    private final CartRepository cartRepository;

    @Transactional
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

            // 🟡 재고 차감
            int newStock = product.getStock() - itemDTO.getQuantity();
            if (newStock < 0) throw new IllegalStateException("재고 부족: " + product.getName());
            product.setStock(newStock);

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

        if (dto.getCartIds() != null && !dto.getCartIds().isEmpty()) {
            cartRepository.deleteByIdIn(dto.getCartIds());
        }

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


    public Map<String, Long> getOrderStatusSummary() {
        Map<String, Long> stats = new HashMap<>();

        // Order 기준
        stats.put("결제완료", orderRepository.countByOrderStatus("결제완료"));
        stats.put("배송중", orderRepository.countByConfirm("배송중"));
        stats.put("구매확정", orderRepository.countByConfirm("구매 확정"));

        // Refund 기준
        stats.put("반품요청", refundRepository.countByChannel("반품"));
        stats.put("교환요청", refundRepository.countByChannel("교환"));

        // 전체 통계
        stats.put("주문건수", orderRepository.count());
        stats.put("주문금액", orderRepository.sumActualMoneyByOrderStatus("결제완료")); // 구매확정된 건만

        stats.put("회원가입", memberRepository.count());
        stats.put("문의게시물", qnaRepository.count());

        return stats;
    }

    public Map<String, Long> getDailyStats() {
        Map<String, Long> map = new HashMap<>();

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        // 주문건수
        map.put("orderCountToday", orderRepository.countByOrderDateBetween(today.atStartOfDay(), today.plusDays(1).atStartOfDay()));
        map.put("orderCountYesterday", orderRepository.countByOrderDateBetween(yesterday.atStartOfDay(), today.atStartOfDay()));

        // 주문금액 (구매확정된 것만)
        map.put("orderMoneyToday", orderRepository.sumActualMoneyByOrderStatusAndOrderDate("결제완료", today.atStartOfDay(), today.plusDays(1).atStartOfDay()));
        map.put("orderMoneyYesterday", orderRepository.sumActualMoneyByOrderStatusAndOrderDate("결제완료", yesterday.atStartOfDay(), today.atStartOfDay()));

        // 회원가입
        map.put("memberToday", memberRepository.countByRegDateBetween(today.atStartOfDay(), today.plusDays(1).atStartOfDay()));
        map.put("memberYesterday", memberRepository.countByRegDateBetween(yesterday.atStartOfDay(), today.atStartOfDay()));

        // 문의 게시물
        map.put("qnaToday", qnaRepository.countByRegDateBetween(today.atStartOfDay(), today.plusDays(1).atStartOfDay()));
        map.put("qnaYesterday", qnaRepository.countByRegDateBetween(yesterday.atStartOfDay(), today.atStartOfDay()));

        return map;
    }


}
