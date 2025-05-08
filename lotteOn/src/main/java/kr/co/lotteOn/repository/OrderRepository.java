package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.member " +
            "LEFT JOIN FETCH o.items i " +
            "LEFT JOIN FETCH i.product " +
            "WHERE o.orderCode = :orderCode")
    Optional<Order> findFullOrderByCode(@Param("orderCode") String orderCode);
}
