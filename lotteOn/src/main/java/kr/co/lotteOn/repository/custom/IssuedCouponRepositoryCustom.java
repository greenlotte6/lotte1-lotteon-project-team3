package kr.co.lotteOn.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponPageRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuedCouponRepositoryCustom {
    public Page<Tuple> searchAll(IssuedCouponPageRequestDTO pageRequestDTO, Pageable pageable);

    public Page<Tuple> findAllForList(Pageable pageable);

    public Page<Tuple> searchAllByMemberId(IssuedCouponPageRequestDTO pageRequestDTO, Pageable pageable);
}
