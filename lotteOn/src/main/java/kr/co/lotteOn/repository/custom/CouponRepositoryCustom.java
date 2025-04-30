package kr.co.lotteOn.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.lotteOn.dto.coupon.CouponPageRequestDTO;
import kr.co.lotteOn.entity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponRepositoryCustom {
    public Page<Tuple> searchCoupons(CouponPageRequestDTO pageRequestDTO, Pageable pageable);
}
