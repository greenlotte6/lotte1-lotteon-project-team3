package kr.co.lotteOn.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteOn.dto.point.PointPageRequestDTO;
import kr.co.lotteOn.entity.Point;
import kr.co.lotteOn.entity.QMember;
import kr.co.lotteOn.entity.QPoint;
import kr.co.lotteOn.repository.custom.PointRepositoryCustom;
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
public class PointRepositoryImpl implements PointRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QMember qMember = QMember.member;
    private final QPoint qPoint = QPoint.point;

    @Override
    public Page<Tuple> searchPoint(PointPageRequestDTO pageRequestDTO, Pageable pageable) {

        String searchType = pageRequestDTO.getSearchType();
        String keyword = pageRequestDTO.getKeyword();

        BooleanExpression expression = null;

        if(searchType.equals("memberId")) {
            expression = qPoint.member.id.contains(keyword);
        }else if(searchType.equals("giveContent")) {
            expression = qPoint.giveContent.contains(keyword);
        }else if(searchType.equals("name")) {
            expression = qPoint.member.name.contains(keyword);
        }

        List<Tuple> tupleList = queryFactory
                .select(qPoint, qMember.id, qMember.name)
                .from(qPoint)
                .join(qMember)
                .on(qPoint.member.id.eq(qMember.id))
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qPoint.pointNo.desc())
                .fetch();

        long total = queryFactory
                .select(qPoint.count())
                .from(qPoint)
                .where(expression)
                .fetchOne();

        return new PageImpl<Tuple>(tupleList, pageable, total);
    }

    @Override
    public Page<Tuple> findAllPoint(Pageable pageable) {
        List<Tuple> tupleList = queryFactory
                .select(qPoint, qMember.id, qMember.name)
                .from(qPoint)
                .join(qMember)
                .on(qPoint.member.id.eq(qMember.id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qPoint.pointNo.desc())
                .fetch();

        long total = queryFactory
                .select(qPoint.count())
                .from(qPoint)
                .fetchOne();

        return new PageImpl<Tuple>(tupleList, pageable, total);
    }

    @Override
    public Page<Tuple> findAllByMemberId(PointPageRequestDTO pageRequestDTO, Pageable pageable) {
        String memberId = pageRequestDTO.getMemberId();
        String period = pageRequestDTO.getPeriod();

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qPoint.member.id.eq(memberId));

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
            builder.and(qPoint.giveDate.between(
                    startDate.atStartOfDay(),
                    endDate.plusDays(1).atStartOfDay()
            ));
        }

        List<Tuple> tupleList = queryFactory
                .select(qPoint, qMember.id)
                .from(qPoint)
                .join(qMember)
                .on(qPoint.member.id.eq(qMember.id))
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qPoint.pointNo.desc())
                .fetch();

        long total = queryFactory
                .select(qPoint.count())
                .from(qPoint)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(tupleList, pageable, total);
    }
}
