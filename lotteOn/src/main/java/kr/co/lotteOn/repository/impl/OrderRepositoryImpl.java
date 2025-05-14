package kr.co.lotteOn.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    private final QDelivery qDelivery = QDelivery.delivery;


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
                .join(qSeller)
                .on(qProduct.companyName.eq(qSeller.companyName))
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
    public Page<Tuple> findTop3ByMemberOrderByOrderDateDesc(Member member) {

        String memberId = member.getId();
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qOrder.member.id.eq(memberId));

        List<Tuple> result = queryFactory
                .select(qOrder, qOrderItem, qProduct, qMember.id)
                .from(qOrder)
                .join(qOrderItem)
                .on(qOrderItem.order.orderCode.eq(qOrder.orderCode))
                .join(qProduct)
                .on(qOrderItem.product.productCode.eq(qProduct.productCode))
                .join(qMember)
                .on(qOrder.member.id.eq(qMember.id))
                .where(builder)
                .orderBy(qOrder.orderDate.desc())
                .limit(3)
                .fetch();

        return new PageImpl<Tuple>(result);
    }

    @Override
    public Page<Tuple> findAll(OrderPageRequestDTO pageRequestDTO, Pageable pageable) {
        String searchType = pageRequestDTO.getSearchType();
        String keyword = pageRequestDTO.getKeyword();

        BooleanBuilder builder = new BooleanBuilder();

        // 검색 조건
        if (searchType != null && !searchType.isBlank()
                && keyword != null && !keyword.isBlank()) {
            BooleanExpression expression = switch (searchType) {
                case "orderCode" -> qOrder.orderCode.contains(keyword);
                case "memberId"  -> qOrder.member.id.contains(keyword);
                case "name"      -> qOrder.member.name.contains(keyword);
                case "payment"   -> qOrder.payment.contains(keyword);
                case "status" -> qOrder.confirm.contains(keyword).or(qOrder.orderStatus.contains(keyword));
                default -> null;
            };
            if (expression != null) builder.and(expression);
        }

        // ✅ 1단계: 페이징된 orderCode 목록 조회
        List<String> orderCodeList = queryFactory
                .select(qOrder.orderCode)
                .from(qOrder)
                .where(builder)
                .orderBy(qOrder.orderDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        if (orderCodeList.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        // ✅ 2단계: 상세 정보 조회 (Tuple로)
        List<Tuple> tupleList = queryFactory
                .select(qOrder, qMember, qOrderItem, qProduct, qSeller)
                .from(qOrder)
                .join(qMember).on(qOrder.member.id.eq(qMember.id))
                .join(qOrderItem).on(qOrderItem.order.orderCode.eq(qOrder.orderCode))
                .join(qProduct).on(qOrderItem.product.productCode.eq(qProduct.productCode))
                .join(qSeller).on(qProduct.companyName.eq(qSeller.companyName))
                .where(qOrder.orderCode.in(orderCodeList))
                .orderBy(qOrder.orderDate.desc())
                .fetch();

        // 전체 개수 조회
        long total = queryFactory
                .select(qOrder.count())
                .from(qOrder)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(tupleList, pageable, total);
    }



    @Override
    public Page<Tuple> findAllByStatus(OrderPageRequestDTO pageRequestDTO, Pageable pageable) {
        String searchType = pageRequestDTO.getSearchType();
        String keyword = pageRequestDTO.getKeyword();

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(
                qOrder.confirm.in(
                        "반품신청 접수",
                        "교환신청 접수",
                        "반품상품 회수중",
                        "교환상품 회수중",
                        "반품처리 완료",
                        "교환처리 완료",
                        "배송준비중"
                )
        );

        // 검색 조건
        if (searchType != null && !searchType.isBlank()
                && keyword != null && !keyword.isBlank()) {
            BooleanExpression expression = switch (searchType) {
                case "orderCode" -> qOrder.orderCode.contains(keyword);
                case "waybill"  -> qDelivery.waybill.contains(keyword);
                case "receiver"      -> qDelivery.receiver.contains(keyword);
                case "post"      -> qDelivery.post.contains(keyword);
                default -> null;
            };
            if (expression != null) builder.and(expression);
        }

        // ✅ 1단계: 페이징된 orderCode 목록 조회
        List<Tuple> orderCodeList = queryFactory
                .select(qOrder.orderCode, qDelivery)
                .from(qOrder)
                .join(qDelivery)
                .on(qOrder.orderCode.eq(qDelivery.orderCode))
                .where(builder)
                .orderBy(qOrder.orderDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        if (orderCodeList.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        // ✅ 2단계: 상세 정보 조회 (Tuple로)
        List<Tuple> tupleList = queryFactory
                .select(qOrder, qMember, qOrderItem, qProduct, qDelivery)
                .from(qOrder)
                .join(qMember)
                .on(qOrder.member.id.eq(qMember.id))
                .join(qOrderItem)
                .on(qOrderItem.order.orderCode.eq(qOrder.orderCode))
                .join(qProduct)
                .on(qOrderItem.product.productCode.eq(qProduct.productCode))
                .join(qDelivery)
                .on(qDelivery.orderCode.eq(qOrder.orderCode))
                .where(qOrder.orderCode.in(orderCodeList.stream()
                        .map(tuple -> tuple.get(qOrder.orderCode))
                        .collect(Collectors.toList()))) // orderCodeList에서 orderCode만 추출하여 필터링
                .orderBy(qOrder.orderDate.desc())
                .fetch();

        // 전체 개수 조회
        long total = queryFactory
                .select(qOrder.count())
                .from(qOrder)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(tupleList, pageable, total);
    }
}
