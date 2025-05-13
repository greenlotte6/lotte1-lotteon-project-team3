package kr.co.lotteOn.service;

import com.querydsl.core.Tuple;
import kr.co.lotteOn.dto.SellerDTO;
import kr.co.lotteOn.dto.order.OrderDTO;
import kr.co.lotteOn.dto.order.OrderPageRequestDTO;
import kr.co.lotteOn.dto.order.OrderPageResponseDTO;
import kr.co.lotteOn.dto.refund.RefundDTO;
import kr.co.lotteOn.dto.refund.RefundPageRequestDTO;
import kr.co.lotteOn.dto.refund.RefundPageResponseDTO;
import kr.co.lotteOn.entity.*;
import kr.co.lotteOn.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    //관리자 주문관리 - 주문현황
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

            // 대표 상품 정보 (첫 번째 항목 기준)
            orderDTO.setCompanyName(product.getCompanyName());
            orderDTO.setDescription(product.getDescription());
            orderDTO.setPrice(product.getPrice());
            orderDTO.setPoint(product.getPoint());
            orderDTO.setProductName(product.getName());
            orderDTO.setImageList(product.getImageList());
            orderDTO.setOrderCode(order.getOrderCode());
            orderDTO.setQuantity(orderItem.getQuantity());
            orderDTO.setProductCode(orderItem.getProduct().getProductCode());
            orderDTO.setTotalPrice(product.getPrice() * orderItem.getQuantity()); // ✅ 추가

            // 판매자 정보 설정
            String companyName = product.getCompanyName();
            Seller sellerInfo = sellerRepository.findByCompanyName(companyName);
            orderDTO.setRating(sellerInfo.getRating());
            orderDTO.setDelegate(sellerInfo.getDelegate());
            orderDTO.setHp(sellerInfo.getHp());
            orderDTO.setBusinessNo(sellerInfo.getBusinessNo());
            orderDTO.setFax(sellerInfo.getFax());
            orderDTO.setAddr1(sellerInfo.getAddr1());
            orderDTO.setAddr2(sellerInfo.getAddr2());
            orderDTO.setZip(sellerInfo.getZip());

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


    //관리자 주문관리 - 배송현황
    public OrderPageResponseDTO DeliveryList(OrderPageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("orderCode");
        Page<Tuple> orders = orderRepository.findAllByStatus(pageRequestDTO, pageable);

        List<OrderDTO> orderDTOList = orders
                .getContent()
                .stream()
                .map(tuple -> {
                    Order order = tuple.get(0, Order.class);
                    Member member = tuple.get(1, Member.class);
                    String memberId = member.getId();
                    String memberName = member.getName();

                    OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
                    orderDTO.setMemberId(memberId);
                    orderDTO.setMemberName(memberName);

                    return orderDTO;
                }).toList();

        int total = (int) orders.getTotalElements();

        return OrderPageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(orderDTOList)
                .total(total)
                .build();
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
