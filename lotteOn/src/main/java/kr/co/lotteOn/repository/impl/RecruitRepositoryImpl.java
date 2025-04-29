package kr.co.lotteOn.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteOn.dto.recruit.RecruitPageRequestDTO;
import kr.co.lotteOn.entity.QRecruit;
import kr.co.lotteOn.entity.Recruit;
import kr.co.lotteOn.repository.custom.RecruitRepositoryCustom;
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
public class RecruitRepositoryImpl implements RecruitRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QRecruit qRecruit = QRecruit.recruit;

    @Override
    public Page<Recruit> searchAllForList(Pageable pageable) {
        List<Recruit> recruitList = queryFactory
                .select(qRecruit)
                .from(qRecruit)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qRecruit.recruitNo.desc())
                .fetch();

        long total = queryFactory.select(qRecruit.count()).from(qRecruit).fetchOne();

        return new PageImpl<>(recruitList, pageable, total);
    }

    @Override
    public Page<Recruit> searchAllForCate(RecruitPageRequestDTO recruitPageRequestDTO, Pageable pageable) {

        String searchType = recruitPageRequestDTO.getSearchType();
        String keyword = recruitPageRequestDTO.getKeyword();

        BooleanExpression expression = null;
        if(searchType.equals("cate")){
            expression = qRecruit.cate.contains(keyword);
        }else if(searchType.equals("employType")){
            expression = qRecruit.employType.contains(keyword);
        }else if(searchType.equals("title")){
            expression = qRecruit.title.contains(keyword);
        }

        List<Recruit> recruitList = queryFactory
                .select(qRecruit)
                .from(qRecruit)
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qRecruit.recruitNo.desc())
                .fetch();

        long total = queryFactory.select(qRecruit.count()).from(qRecruit).where(expression).fetchOne();



        return new PageImpl<>(recruitList, pageable, total);
    }
}
