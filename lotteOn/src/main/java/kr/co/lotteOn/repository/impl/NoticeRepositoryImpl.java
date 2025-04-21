package kr.co.lotteOn.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteOn.dto.NoticePageRequestDTO;
import kr.co.lotteOn.entity.Notice;
import kr.co.lotteOn.entity.QMember;
import kr.co.lotteOn.entity.QNotice;
import kr.co.lotteOn.repository.custom.NoticeRepositoryCustom;
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
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QNotice qNotice = QNotice.notice;
    private QMember qMember = QMember.member;

    @Override
    public Page<Notice> searchAllForList(Pageable pageable) {
        List<Notice> noticeList = queryFactory
                .select(qNotice)
                .from(qNotice)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qNotice.noticeNo.desc())
                .fetch();

        long total = queryFactory.select(qNotice.count()).from(qNotice).fetchOne();

        //페이징처리를 위한 객체 반환
        return new PageImpl<Notice>(noticeList, pageable, total);
    }

    @Override
    public Page<Tuple> searchAllForSearch(NoticePageRequestDTO noticePageRequestDTO, Pageable pageable) {
        return null;
    }
}
