package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.IssuedCoupon;
import kr.co.lotteOn.repository.custom.IssuedCouponRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuedCouponRepository extends JpaRepository<IssuedCoupon, Integer>, IssuedCouponRepositoryCustom {
}
