package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findByOrder_OrderCode(String orderCode);

    OrderItem findFirstByOrder_OrderCode(String orderCode);
}
