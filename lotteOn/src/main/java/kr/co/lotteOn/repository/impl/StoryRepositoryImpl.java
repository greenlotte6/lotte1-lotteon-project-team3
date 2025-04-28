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

        String searchType = storyPageRequestDTO.getSearchType();
        String keyword = storyPageRequestDTO.getKeyword();

        BooleanExpression expression = null;

        if(searchType.equals("title")){
            expression = qStory.title.contains(keyword);
        }else if(searchType.equals("cate")){
            expression = qStory.cate.contains(keyword);
        }

        List<Story> storyList = queryFactory
                .select(qStory)
                .from(qStory)
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qStory.storyNo.desc())
                .fetch();

        long total = queryFactory.select(qStory.count()).from(qStory).fetchOne();

        return new PageImpl<>(storyList, pageable, total);
    }
}
