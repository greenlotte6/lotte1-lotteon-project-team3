package kr.co.lotteOn.service;

import com.querydsl.core.Tuple;
import jakarta.transaction.Transactional;
import kr.co.lotteOn.dto.delivery.DeliveryDTO;
import kr.co.lotteOn.dto.delivery.DeliveryPageRequestDTO;
import kr.co.lotteOn.dto.delivery.DeliveryPageResponseDTO;
import kr.co.lotteOn.dto.order.OrderDTO;
import kr.co.lotteOn.dto.order.OrderPageRequestDTO;
import kr.co.lotteOn.dto.order.OrderPageResponseDTO;
import kr.co.lotteOn.dto.refund.RefundDTO;
import kr.co.lotteOn.dto.refund.RefundPageRequestDTO;
import kr.co.lotteOn.dto.refund.RefundPageResponseDTO;
import kr.co.lotteOn.entity.*;
import kr.co.lotteOn.repository.*;
import kr.co.lotteOn.util.DeliveryCodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminOrderService {
    private final MemberRepository memberRepository;
    private final RefundRepository refundRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;
    private final SellerRepository sellerRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryCodeGenerator codeGenerator;

    //관리자 주문관리 - 주문현황
    @Transactional
    public OrderPageResponseDTO OrderList(OrderPageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("orderCode");
        Page<Tuple> orders = orderRepository.findAll(pageRequestDTO, pageable);

        Map<String, OrderDTO> orderMap = new LinkedHashMap<>();

        for (Tuple tuple : orders.getContent()) {
            Order order = tuple.get(0, Order.class);
            Member member = tuple.get(1, Member.class);
            OrderItem orderItem = tuple.get(2, OrderItem.class);
            Product product = tuple.get(3, Product.class);
            Seller seller = tuple.get(4, Seller.class);

            String orderCode = order.getOrderCode();

            // 이미 존재하면 수량과 totalPrice 누적
            if (orderMap.containsKey(orderCode)) {
                OrderDTO existing = orderMap.get(orderCode);

                // 수량 누적
                int prevQty = existing.getQuantity();
                existing.setQuantity(prevQty + orderItem.getQuantity());

                // 총 가격 누적
                int prevTotal = existing.getTotalPrice();
                int added = product.getPrice() * orderItem.getQuantity();
                existing.setTotalPrice(prevTotal + added);

                continue;
            }

            // 처음 등장한 orderCode라면 새 OrderDTO 생성
            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
            orderDTO.setMemberId(member.getId());
            orderDTO.setMemberName(member.getName());
            orderDTO.setMember(member);

            // ✅ 한 줄로 설정
            orderDTO.setProductInfo(product, orderItem);
            orderDTO.setSellerInfo(seller);

            // orderMap에 저장
            orderMap.put(orderCode, orderDTO);
        }

        List<OrderDTO> orderDTOList = new ArrayList<>(orderMap.values());
        int total = (int) orders.getTotalElements();

        return OrderPageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(orderDTOList)
                .total(total)
                .build();
    }

    public int deliveryWrite(DeliveryDTO deliveryDTO) {

        Delivery savedDelivery = codeGenerator.createDelivery(deliveryDTO);
        Order order = orderRepository.findByOrderCode(deliveryDTO.getOrderCode());
        Refund refund = refundRepository.findByOrderCode(deliveryDTO.getOrderCode());

        // 주문 상태 업데이트
        order.setConfirm("배송준비중");
        orderRepository.save(order);

        // 환불이 존재할 경우에만 상태 업데이트
        if (refund != null) {
            refund.setStatus("상품회수중");
            refundRepository.save(refund);
        } else {
            log.info("환불 정보가 없는 주문입니다. 주문코드: {}", deliveryDTO.getOrderCode());
        }

        return savedDelivery.getDeliveryNo();
    }



    //관리자 주문관리 - 배송현황
    @Transactional
    public DeliveryPageResponseDTO DeliveryList(DeliveryPageRequestDTO pageRequestDTO) {
        //페이징
        Pageable pageable = pageRequestDTO.getPageable("deliveryNo");
        Page<Tuple> pageDelivery = deliveryRepository.findAll(pageRequestDTO, pageable);
        
        Map<String, DeliveryDTO> deliveryMap = new LinkedHashMap<>();

        for (Tuple tuple : pageDelivery.getContent()) {
            Delivery delivery = tuple.get(0, Delivery.class);
            Order order = tuple.get(1, Order.class);
            OrderItem orderItem = tuple.get(2, OrderItem.class);
            Product product = tuple.get(3, Product.class);
            Member member = tuple.get(4, Member.class);

            String orderCode = delivery.getOrderCode();

            if (deliveryMap.containsKey(orderCode)) {
                DeliveryDTO existing = deliveryMap.get(orderCode);

                int prevQty = existing.getQuantity();
                existing.setQuantity(prevQty + orderItem.getQuantity());

                int prevTotal = existing.getTotalPrice();
                int added = product.getPrice() * orderItem.getQuantity();
                existing.setTotalPrice(prevTotal + added);

                continue;
            }

            // ✅ regDate 기준 배송 상태 판단
            LocalDate regDate = delivery.getRegDate().toLocalDate(); // LocalDateTime이면 .toLocalDate() 사용
            LocalDate today = LocalDate.now();
            // 기준일 계산
            LocalDate readyDate = regDate.plusDays(1); // 배송중 기준
            LocalDate completeDate = regDate.plusDays(3); // 배송완료 기준

            if (order.getRefundStatus() != null) {
                // 환불 상태가 있으면 수거 상태로 변경
                if (!today.isBefore(completeDate)) {
                    order.setConfirm("수거완료");
                } else if (!today.isBefore(readyDate)) {
                    order.setConfirm("수거중");
                }
            } else {
                // 환불 상태가 없으면 기존 배송 상태로
                if (!today.isBefore(completeDate)) {
                    order.setConfirm("배송완료");
                } else if (!today.isBefore(readyDate)) {
                    order.setConfirm("배송중");
                }
            }

            // ✅ DTO 생성 및 매핑
            DeliveryDTO deliveryDTO = modelMapper.map(delivery, DeliveryDTO.class);
            deliveryDTO.setMemberId(member.getId());
            deliveryDTO.setMemberName(member.getName());
            deliveryDTO.setMember(member);
            deliveryDTO.setHp(member.getHp());

            deliveryDTO.setProductInfo(product, orderItem);
            deliveryDTO.setOrderInfo(order);

            deliveryMap.put(orderCode, deliveryDTO);

        }

        List<DeliveryDTO> deliveryDTOList = new ArrayList<>(deliveryMap.values());
        int total = (int) pageDelivery.getTotalElements();

        return DeliveryPageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(deliveryDTOList)
                .total(total)
                .build();
    }

    @Transactional
    public void updateDeliveryStatusByDate() {
        List<Delivery> deliveries = deliveryRepository.findAll();

        for (Delivery delivery : deliveries) {
            LocalDate regDate = delivery.getRegDate().toLocalDate();
            LocalDate today = LocalDate.now();

            String code = delivery.getOrderCode();

            Order order = orderRepository.findByOrderCode(code);

            if (!today.isBefore(regDate.plusDays(3))) {
                order.setConfirm("배송완료");
            } else if (!today.isBefore(regDate.plusDays(1))) {
                order.setConfirm("배송중");
            }
        }
        // 트랜잭션 안이므로 변경 감지(Dirty Checking)로 자동 저장됨
    }

    //관리자 환불/교환 신청 내역 불러오기
    public RefundPageResponseDTO RefundList(RefundPageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("refundNo");
        Page<Refund> pageRefund = refundRepository.findAll(pageable);

        List<RefundDTO> refundList = pageRefund
                .stream()
                .map(refund -> {
                    RefundDTO refundDTO = modelMapper.map(refund, RefundDTO.class);

                    refundDTO.setMemberId(refund.getMember().getId());

                    return refundDTO;
                }).toList();

        int total = (int) pageRefund.getTotalElements();

        return RefundPageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(refundList)
                .total(total)
                .build();
    }
}
