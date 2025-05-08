package kr.co.lotteOn.repository;

import kr.co.lotteOn.dto.order.OrderPageRequestDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Order;
import kr.co.lotteOn.repository.custom.OrderRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> ,
        OrderRepositoryCustom {
    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.member " +
            "LEFT JOIN FETCH o.items i " +
            "LEFT JOIN FETCH i.product " +
            "WHERE o.orderCode = :orderCode")
    Optional<Order> findFullOrderByCode(@Param("orderCode") String orderCode);

    int countByMember_Id(String memberId);

    Order findByOrderCode(String orderCode);
}

