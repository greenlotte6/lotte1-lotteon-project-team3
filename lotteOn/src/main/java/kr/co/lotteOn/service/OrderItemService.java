package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.OrderItemDTO;
import kr.co.lotteOn.entity.Order;
import kr.co.lotteOn.entity.OrderItem;
import kr.co.lotteOn.entity.Product;
import kr.co.lotteOn.repository.OrderItemRepository;
import kr.co.lotteOn.repository.OrderRepository;
import kr.co.lotteOn.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public void saveOrderItems(String orderCode, List<OrderItemDTO> itemDTOs) {
        Order order = orderRepository.findById(orderCode)
                .orElseThrow(() -> new IllegalArgumentException("주문 정보가 없습니다: " + orderCode));

        for (OrderItemDTO dto : itemDTOs) {
            Product product = productRepository.findByProductCode(dto.getProductCode())
                    .orElseThrow(() -> new IllegalArgumentException("상품 정보가 없습니다: " + dto.getProductCode()));

            OrderItem item = OrderItemDTO.toEntity(order, product, dto);
            orderItemRepository.save(item);
        }
    }

    @Transactional(readOnly = true)
    public List<OrderItem> getItemsByOrderCode(String orderCode) {
        return orderItemRepository.findByOrder_OrderCode(orderCode);
    }

}
