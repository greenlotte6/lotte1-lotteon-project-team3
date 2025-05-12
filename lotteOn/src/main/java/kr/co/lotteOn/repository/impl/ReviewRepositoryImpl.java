package kr.co.lotteOn.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteOn.dto.review.ReviewPageRequestDTO;
import kr.co.lotteOn.entity.*;
import kr.co.lotteOn.repository.custom.ReviewRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QMember qMember = QMember.member;
    private final QOrder qOrder= QOrder.order;
    private final QOrderItem qOrderItem = QOrderItem.orderItem;
    private final QReview qReview = QReview.review;
    private final QProduct qProduct = QProduct.product;

    @Override
    public Page<Tuple> findAllByMember_Id(ReviewPageRequestDTO pageRequestDTO, Pageable pageable) {
        String memberId = pageRequestDTO.getWriter();

        List<Tuple> tupleList = queryFactory
                .select(qReview, qProduct, qMember)
                .from(qReview)
                .join(qProduct)
                .on(qReview.productCode.eq(qProduct.productCode))
                .join(qMember)
                .on(qReview.member.id.eq(qMember.id))
                .where(qReview.member.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qReview.regDate.desc())
                .fetch();

        long total = queryFactory
                .select(qReview.count())
                .from(qReview)
                .where(qReview.member.id.eq(memberId))
                .fetchOne();

        return new PageImpl<Tuple>(tupleList, pageable, total);
    }

    @Override
    public Page<Tuple> findTop3ByMemberOrderByRegDateDesc(Member member) {
        String memberId = member.getId();

        List<Tuple> result = queryFactory
                .select(qReview, qProduct, qMember)
                .from(qReview)
                .join(qProduct)
                .on(qReview.productCode.eq(qProduct.productCode))
                .join(qMember)
                .on(qReview.member.id.eq(qMember.id))
                .where(qReview.member.id.eq(memberId))
                .limit(3)
                .orderBy(qReview.regDate.desc())
                .fetch();

        return new PageImpl<Tuple>(result);
    }
}
