package kr.co.lotteOn.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteOn.dto.coupon.CouponPageRequestDTO;
import kr.co.lotteOn.entity.Coupon;
import kr.co.lotteOn.entity.QCoupon;
import kr.co.lotteOn.entity.QSeller;
import kr.co.lotteOn.repository.custom.CouponRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CouponRepositoryImpl implements CouponRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QCoupon qCoupon = QCoupon.coupon;
    private final QSeller qSeller = QSeller.seller;

    @Override
    public Page<Tuple> searchCoupons(CouponPageRequestDTO pageRequestDTO, Pageable pageable) {

        String searchType = pageRequestDTO.getSearchType();
        String keyword = pageRequestDTO.getKeyword();

        BooleanExpression expression = null;

        if(searchType.equals("couponName")) {
            expression = qCoupon.couponName.contains(keyword);
        }else if(searchType.equals("companyName")) {
            expression = qCoupon.companyName.contains(keyword);
        }else if(searchType.equals("couponType")) {
            expression = qCoupon.couponType.contains(keyword);
        }

        List<Tuple> tupleList = queryFactory
                .select(qCoupon, qSeller.companyName)
                .from(qCoupon)
                .join(qSeller)
                .on(qCoupon.companyName.eq(qSeller.companyName))
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qCoupon.issuedNo.desc())
                .fetch();

        long total = queryFactory
                .select(qCoupon.count())
                .from(qCoupon)
                .join(qSeller)
                .on(qCoupon.companyName.eq(qSeller.companyName))
                .where(expression)
                .fetchOne();

        return new PageImpl<Tuple>(tupleList, pageable, total);
    }

}
