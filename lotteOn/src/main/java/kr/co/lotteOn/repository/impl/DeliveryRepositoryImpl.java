package kr.co.lotteOn.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteOn.dto.delivery.DeliveryPageRequestDTO;
import kr.co.lotteOn.entity.*;
import kr.co.lotteOn.repository.custom.DeliveryRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class DeliveryRepositoryImpl implements DeliveryRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QMember qMember = QMember.member;
    private final QOrder qOrder = QOrder.order;
    private final QProduct qProduct = QProduct.product;
    private final QOrderItem qOrderItem = QOrderItem.orderItem;
    private final QDelivery qDelivery = QDelivery.delivery;

    @Override
    public Page<Tuple> findAllByStatus(DeliveryPageRequestDTO pageRequestDTO, Pageable pageable) {
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
                case "orderCode" -> qDelivery.orderCode.contains(keyword);
                case "waybill"  -> qDelivery.waybill.contains(keyword);
                case "receiver"      -> qDelivery.receiver.contains(keyword);
                default -> null;
            };
            if (expression != null) builder.and(expression);
        }

        // ✅ 1단계: 페이징된 orderCode 목록 조회
        List<String> deliveryCodeList = queryFactory
                .select(qDelivery.orderCode)
                .from(qDelivery)
                .where(builder)
                .orderBy(qDelivery.regDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        if (deliveryCodeList.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        // ✅ 2단계: 상세 정보 조회 (Tuple로)
        List<Tuple> tupleList = queryFactory
                .select(qOrder, qMember, qOrderItem, qProduct, qDelivery)
                .from(qDelivery)
                .join(qMember)
                .on(qOrder.member.id.eq(qMember.id))
                .join(qOrderItem)
                .on(qOrderItem.order.orderCode.eq(qOrder.orderCode))
                .join(qProduct)
                .on(qOrderItem.product.productCode.eq(qProduct.productCode))
                .join(qOrder)
                .on(qDelivery.orderCode.eq(qOrder.orderCode))
                .where(qDelivery.orderCode.in(deliveryCodeList))
                .orderBy(qOrder.orderDate.desc())
                .fetch();

        // 전체 개수 조회
        long total = queryFactory
                .select(qDelivery.count())
                .from(qDelivery)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(tupleList, pageable, total);
    }

    @Override
    public Page<Tuple> findAll(DeliveryPageRequestDTO pageRequestDTO, Pageable pageable) {
        String searchType = pageRequestDTO.getSearchType();
        String keyword = pageRequestDTO.getKeyword();

        BooleanBuilder builder = new BooleanBuilder();


        // 검색 조건
        if (searchType != null && !searchType.isBlank()
                && keyword != null && !keyword.isBlank()) {
            BooleanExpression expression = switch (searchType) {
                case "orderCode" -> qDelivery.orderCode.contains(keyword);
                case "waybill"  -> qDelivery.waybill.contains(keyword);
                case "receiver"      -> qDelivery.receiver.contains(keyword);
                default -> null;
            };
            if (expression != null) builder.and(expression);
        }

        List<Tuple> tupleList = queryFactory
                .select(qDelivery, qOrder, qOrderItem, qProduct, qMember)
                .from(qDelivery)
                .join(qOrder)
                .on(qDelivery.orderCode.eq(qOrder.orderCode))
                .join(qOrderItem)
                .on(qOrderItem.order.orderCode.eq(qOrder.orderCode))
                .join(qProduct)
                .on(qOrderItem.product.productCode.eq(qProduct.productCode))
                .join(qMember)
                .on(qOrder.member.id.eq(qMember.id))
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qDelivery.regDate.desc())
                .fetch();


        long total = queryFactory
                .select(qDelivery.count())
                .from(qDelivery)
                .where(builder)
                .fetchOne();


        return new PageImpl<>(tupleList, pageable, total);
    }
}
