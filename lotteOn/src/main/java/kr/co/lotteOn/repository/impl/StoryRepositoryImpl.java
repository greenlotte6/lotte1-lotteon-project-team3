package kr.co.lotteOn.repository.impl;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteOn.dto.story.StoryPageRequestDTO;
import kr.co.lotteOn.entity.QStory;
import kr.co.lotteOn.entity.Story;
import kr.co.lotteOn.repository.custom.StoryRepositoryCustom;
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
public class StoryRepositoryImpl implements StoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QStory qStory = QStory.story;

    @Override
    public Page<Story> searchAllForList(Pageable pageable) {

        List<Story> storyList = queryFactory
                .select(qStory)
                .from(qStory)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qStory.storyNo.desc())
                .fetch();

        long total = queryFactory.select(qStory.count()).from(qStory).fetchOne();

        return new PageImpl<>(storyList, pageable, total);
    }

    @Override
    public Page<Story> searchAllForCate(StoryPageRequestDTO storyPageRequestDTO, Pageable pageable) {
        String cate = storyPageRequestDTO.getCate();

        BooleanExpression expression = qStory.storyNo.isNotNull(); // 기본 always true

        // cate가 null이 아니고, 공백이 아닌 경우
        if (cate != null && !cate.isEmpty()) {
            expression = qStory.cate.eq(cate); // equals()로 정확히 일치하는 값만 찾기
        }

        List<Story> storyList = queryFactory
                .select(qStory)
                .from(qStory)
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qStory.storyNo.desc())
                .fetch();

        String sql = queryFactory
                .select(qStory)
                .from(qStory)
                .where(expression)
                .toString();  // 쿼리 출력

        System.out.println("Generated SQL: " + sql);
        System.out.println("Generated SQL: " + sql);
        System.out.println("Generated SQL: " + sql);
        System.out.println("Generated SQL: " + sql);
        System.out.println("Generated SQL: " + sql);
        System.out.println("Generated SQL: " + sql);

        long total = queryFactory.select(qStory.count()).from(qStory).where(expression).fetchOne();

        return new PageImpl<>(storyList, pageable, total);
    }
}
