package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByMember_Id(String id);

    Optional<Cart> findByMember_IdAndProduct_Id(String memberId, Long productId);

    @Query("SELECT c FROM Cart c JOIN FETCH c.product WHERE c.member.id = :memberId")
    List<Cart> findWithProductByMemberId(@Param("memberId") String memberId);

    @Query("SELECT c FROM Cart c JOIN FETCH c.product WHERE c.id = :cartId")
    Optional<Cart> findWithProductById(@Param("cartId") Long cartId);

    void deleteByIdIn(List<Long> ids);
}
