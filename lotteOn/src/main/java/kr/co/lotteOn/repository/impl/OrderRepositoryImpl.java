package kr.co.lotteOn.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteOn.dto.order.OrderPageRequestDTO;
import kr.co.lotteOn.entity.*;
import kr.co.lotteOn.repository.custom.OrderRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QMember qMember = QMember.member;
    private final QOrder qOrder = QOrder.order;
    private final QPoint qPoint = QPoint.point;
    private final QProduct qProduct = QProduct.product;
    private final QOrderItem qOrderItem = QOrderItem.orderItem;
    private final QSeller qSeller = QSeller.seller;


    @Override
    public Page<Tuple> findAllByMember_Id(OrderPageRequestDTO pageRequestDTO, Pageable pageable) {
        String memberId = pageRequestDTO.getMemberId();
        String period = pageRequestDTO.getPeriod();

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qOrder.member.id.eq(memberId));

        //날짜검색조건
        LocalDate startDate = null;
        LocalDate endDate = null;

        if (period != null && !period.isEmpty()) {
            switch (period) {
                case "1week":
                    endDate = LocalDate.now();
                    startDate = endDate.minusWeeks(1);
                    break;
                case "1month":
                    endDate = LocalDate.now();
                    startDate = endDate.minusMonths(1);
                    break;
                case "3month":
                    endDate = LocalDate.now();
                    startDate = endDate.minusMonths(3);
                    break;
                case "custom":
                    endDate = pageRequestDTO.getEndDate();
                    startDate = pageRequestDTO.getStartDate();
                    break;
            }
        }

        // 날짜 필터 조건이 설정된 경우에만 builder에 추가
        if (startDate != null && endDate != null) {
            builder.and(qOrder.orderDate.between(
                    startDate.atStartOfDay(),
                    endDate.plusDays(1).atStartOfDay()
            ));
        }

        List<Tuple> tupleList = queryFactory
                .select(qOrder,qOrderItem, qProduct, qMember, qSeller)
                .from(qOrder)
                .join(qOrderItem)
                .on(qOrderItem.order.orderCode.eq(qOrder.orderCode))
                .join(qProduct)
                .on(qOrderItem.product.productCode.eq(qProduct.productCode))
                .join(qMember)
                .on(qOrder.member.id.eq(qMember.id))
                /*.join(qSeller)
                .on(qProduct.companyName.eq(qSeller.companyName))*/
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qOrder.orderDate.desc())
                .fetch();

        long total = queryFactory
                .select(qOrder.count())
                .from(qOrder)
                .where(builder)
                .fetchOne();

        return new PageImpl<Tuple>(tupleList, pageable, total);
    }

    @Override
    public Page<Tuple> findTop3ByMemberOrderByOrderDateDesc(Member memberId) {

        List<Tuple> result = queryFactory
                .select(qOrder, qOrderItem, qProduct, qMember.id)
                .from(qOrder)
                .join(qOrderItem)
                .on(qOrderItem.order.orderCode.eq(qOrder.orderCode))
                .join(qProduct)
                .on(qOrderItem.product.productCode.eq(qProduct.productCode))
                .join(qMember)
                .on(qOrder.member.id.eq(qMember.id))
                .where(qOrder.member.id.eq(qMember.id))
                .orderBy(qOrder.orderDate.desc())
                .limit(3)
                .fetch();

        return new PageImpl<Tuple>(result);
    }

    @Override
    public Page<Tuple> searchOrderByMember_IdAndOrderDate(OrderPageRequestDTO pageRequestDTO, Pageable pageable) {
        return null;
    }
}
