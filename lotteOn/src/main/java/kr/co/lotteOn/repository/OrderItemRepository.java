package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.OrderItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findByOrder_OrderCode(String orderCode);

    OrderItem findFirstByOrder_OrderCode(String orderCode);

    @Query("SELECT oi.product.productCode FROM OrderItem oi GROUP BY oi.product.productCode ORDER BY COUNT(oi.product.productCode) DESC")
    List<String> findTopPopularProductCodes(Pageable pageable);

}
