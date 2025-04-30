package kr.co.lotteOn.repository.impl;


import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponPageRequestDTO;
import kr.co.lotteOn.entity.QCoupon;
import kr.co.lotteOn.entity.QIssuedCoupon;
import kr.co.lotteOn.entity.QMember;
import kr.co.lotteOn.entity.QSeller;
import kr.co.lotteOn.repository.custom.IssuedCouponRepositoryCustom;
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
public class IssuedCouponRepositoryImpl implements IssuedCouponRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QIssuedCoupon qIssuedCoupon = QIssuedCoupon.issuedCoupon;
    private final QCoupon qCoupon = QCoupon.coupon;
    private final QMember qMember = QMember.member;
    private final QSeller qSeller = QSeller.seller;

    @Override
    public Page<Tuple> searchAll(IssuedCouponPageRequestDTO pageRequestDTO, Pageable pageable) {
        String searchType = pageRequestDTO.getSearchType();
        String keyword = pageRequestDTO.getKeyword();

        BooleanExpression expression = null;

        if(searchType.equals("couponName")) {
            expression = qIssuedCoupon.coupon.couponName.contains(keyword);
        }else if(searchType.equals("couponCode")){
            expression = qIssuedCoupon.coupon.couponCode.contains(keyword);
        }else if(searchType.equals("couponType")){
            expression = qIssuedCoupon.coupon.couponType.contains(keyword);
        }else if(searchType.equals("memberId")){
            expression = qIssuedCoupon.member.id.contains(keyword);
        }

        List<Tuple> tupleList = queryFactory
                .select(qIssuedCoupon, qCoupon, qMember.id)
                .from(qIssuedCoupon)
                .join(qCoupon)
                .on(qIssuedCoupon.coupon.couponCode.eq(qCoupon.couponCode))
                .join(qMember)
                .on(qIssuedCoupon.member.id.eq(qMember.id))
                .join(qSeller)
                .on(qIssuedCoupon.coupon.companyName.eq(qSeller.companyName))
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qIssuedCoupon.issuedNo.desc())
                .fetch();

        long total = queryFactory
                .select(qIssuedCoupon.count())
                .from(qIssuedCoupon)
                .join(qCoupon)
                .on(qIssuedCoupon.coupon.couponCode.eq(qCoupon.couponCode))
                .join(qMember)
                .on(qIssuedCoupon.member.id.eq(qMember.id))
                .join(qSeller)
                .on(qIssuedCoupon.coupon.companyName.eq(qSeller.companyName))
                .where(expression)
                .fetchOne();

        return new PageImpl<Tuple>(tupleList, pageable, total);
    }

    @Override
    public Page<Tuple> findAllForList(Pageable pageable) {
        List<Tuple> tupleList = queryFactory
                .select(qIssuedCoupon, qCoupon, qMember.id)
                .from(qIssuedCoupon)
                .join(qCoupon)
                .on(qIssuedCoupon.coupon.couponCode.eq(qCoupon.couponCode))
                .join(qMember)
                .on(qIssuedCoupon.member.id.eq(qMember.id))
                .join(qSeller)
                .on(qIssuedCoupon.coupon.companyName.eq(qSeller.companyName))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qIssuedCoupon.issuedNo.desc())
                .fetch();

        long total = queryFactory
                .select(qIssuedCoupon.count())
                .from(qIssuedCoupon)
                .fetchOne();

        return new PageImpl<Tuple>(tupleList, pageable, total);
    }

    @Override
    public Page<Tuple> searchAllByMemberId(IssuedCouponPageRequestDTO pageRequestDTO, Pageable pageable) {
        String memberId = pageRequestDTO.getMemberId();

        List<Tuple> tupleList = queryFactory
                .select(qIssuedCoupon, qCoupon, qMember.id)
                .from(qIssuedCoupon)
                .join(qCoupon)
                .on(qIssuedCoupon.coupon.couponCode.eq(qCoupon.couponCode))
                .join(qMember)
                .on(qIssuedCoupon.member.id.eq(qMember.id))
                .join(qSeller)
                .on(qIssuedCoupon.coupon.companyName.eq(qSeller.companyName))
                .where(qIssuedCoupon.member.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qIssuedCoupon.issuedNo.desc())
                .fetch();

        long total = queryFactory
                .select(qIssuedCoupon.count())
                .from(qIssuedCoupon)
                .where(qIssuedCoupon.member.id.eq(memberId))
                .fetchOne();

        return new PageImpl<>(tupleList, pageable, total);
    }
}
