package kr.co.lotteOn.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteOn.dto.point.PointPageRequestDTO;
import kr.co.lotteOn.entity.QMember;
import kr.co.lotteOn.entity.QPoint;
import kr.co.lotteOn.repository.custom.PointRepositoryCustom;
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
}
