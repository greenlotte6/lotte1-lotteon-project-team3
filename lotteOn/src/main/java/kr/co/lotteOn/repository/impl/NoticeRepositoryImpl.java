package kr.co.lotteOn.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteOn.dto.notice.NoticePageRequestDTO;
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
    public Page<Notice> searchAllForCate(NoticePageRequestDTO noticePageRequestDTO, Pageable pageable) {
        String cate = noticePageRequestDTO.getCate();

        //카테고리 선택
        BooleanExpression expression = null;
        if(cate.equals("고객서비스")){
            expression = qNotice.cate.like(cate + "%");
        }else if(cate.equals("이벤트당첨")){
            expression = qNotice.cate.like(cate + "%");
        }else if(cate.equals("안전거래")){
            expression = qNotice.cate.like(cate + "%");
        }else if(cate.equals("위해상품")){
            expression = qNotice.cate.like(cate + "%");
        }else if(cate.equals("")){
            expression = qNotice.cate.eq(cate);
        }

        List<Notice> noticeList = queryFactory
                .select(qNotice)
                .from(qNotice)
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qNotice.noticeNo.desc())
                .fetch();

        long total = queryFactory
                .select(qNotice.count())
                .from(qNotice)
                .where(expression)
                .fetchOne();


        //페이징처리를 위한 객체 반환
        return new PageImpl<Notice>(noticeList, pageable, total);
    }
}
