package kr.co.lotteOn.repository;

import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponDTO;
import kr.co.lotteOn.entity.Coupon;
import kr.co.lotteOn.entity.IssuedCoupon;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.repository.custom.IssuedCouponRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssuedCouponRepository extends JpaRepository<IssuedCoupon, Integer>, IssuedCouponRepositoryCustom {
    @Query("SELECT new kr.co.lotteOn.dto.issuedCoupon.IssuedCouponDTO(ic.issuedNo, c.couponName, c.benefit, c.couponType) " +
            "FROM IssuedCoupon ic JOIN ic.coupon c WHERE ic.member.id = :memberId AND ic.status = '미사용'")
    public List<IssuedCouponDTO> findAvailableCouponsByMemberId(String memberId);

    public int countByMember_Id(String memberId);

    Optional<IssuedCoupon> findByIssuedNo(int issuedNo);

}
