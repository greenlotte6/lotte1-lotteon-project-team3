package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Coupon;
import kr.co.lotteOn.repository.custom.CouponRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer>, CouponRepositoryCustom {
    public Optional<Coupon> findByCouponCode(String couponCode);

    List<Coupon> findByStatus(String status);

}
