package kr.co.lotteOn.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteOn.dto.qna.QnaDTO;
import kr.co.lotteOn.dto.qna.QnaPageRequestDTO;
import kr.co.lotteOn.entity.QMember;
import kr.co.lotteOn.entity.QQna;
import kr.co.lotteOn.entity.Qna;
import kr.co.lotteOn.repository.custom.QnaRepositoryCustom;
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
public class QnaRepositoryImpl implements QnaRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QQna qQna = QQna.qna;
    private final QMember qMember = QMember.member;

    @Override
    public Page<Qna> searchAllForList(Pageable pageable) {
        List<Qna> qnaList = queryFactory
                .select(qQna)
                .from(qQna)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qQna.qnaNo.desc())
                .fetch();

        long total = queryFactory.select(qQna.count()).from(qQna).fetchOne();

        return new PageImpl<>(qnaList, pageable, total);
    }

    @Override
    public Page<Qna> searchAllForCate(QnaPageRequestDTO qnaPageRequestDTO, Pageable pageable) {
        String cate1 = qnaPageRequestDTO.getCate1();
        String cate2 = qnaPageRequestDTO.getCate2();

        BooleanExpression expression = null;

        if(cate1.equals("member") || cate2.equals("가입")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("member") || cate2.equals("탈퇴")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("member") || cate2.equals("회원정보")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("member") || cate2.equals("로그인")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("event") && cate2.equals("쿠폰/할인혜택")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("event") && cate2.equals("포인트")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("event") && cate2.equals("제휴")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("event") && cate2.equals("이벤트")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("order") && cate2.equals("상품")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("order") && cate2.equals("결제")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("order") && cate2.equals("구매내역")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("order") && cate2.equals("영수증/증빙")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("delivery") && cate2.equals("배송상태/기간")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("delivery") && cate2.equals("배송정보확인/변경")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("delivery") && cate2.equals("해외배송")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("delivery") && cate2.equals("당일배송")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("delivery") && cate2.equals("해외직구")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("cancel") && cate2.equals("반품신청/철회")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("cancel") && cate2.equals("반품정보확인/변경")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("cancel") && cate2.equals("교환AS신청/철회")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("cancel") && cate2.equals("교환정보확인/변경")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("cancel") && cate2.equals("취소신청/철회")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("cancel") && cate2.equals("취소확인/환불정보")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("travel") && cate2.equals("여행/숙박")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("travel") && cate2.equals("항공")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("safe") && cate2.equals("청소년")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("safe") && cate2.equals("서비스")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("safe") && cate2.equals("지식재산권침해")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("safe") && cate2.equals("법령")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("safe") && cate2.equals("게시물")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("safe") && cate2.equals("직거래/외부거래유도")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }else if(cate1.equals("safe") && cate2.equals("표시광고")) {
            expression = qQna.cate1.eq(cate1).and(qQna.cate2.eq(cate2));
        }

        List<Qna> qnaList = queryFactory
                .select(qQna)
                .from(qQna)
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qQna.qnaNo.desc())
                .fetch();

        long total = queryFactory
                .select(qQna.count())
                .from(qQna)
                .where(expression)
                .fetchOne();

        return new PageImpl<>(qnaList, pageable, total);
    }

    @Override
    public Page<Tuple> searchAllByWriter(QnaPageRequestDTO qnaPageRequestDTO, Pageable pageable) {
        String writer = qnaPageRequestDTO.getWriter();

        List<Tuple> tupleList = queryFactory
                .select(qQna, qMember.id)
                .from(qQna)
                .join(qQna.writer, qMember)
                .where(qQna.writer.id.eq(writer))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qQna.qnaNo.desc())
                .fetch();

        long total = queryFactory
                .select(qQna.count())
                .from(qQna)
                .where(qQna.writer.id.eq(writer))
                .fetchOne();

        return new PageImpl<>(tupleList, pageable, total);
    }
}
