package kr.co.lotteOn.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteOn.dto.faq.FaqPageRequestDTO;
import kr.co.lotteOn.entity.Faq;
import kr.co.lotteOn.entity.QFaq;
import kr.co.lotteOn.entity.QMember;
import kr.co.lotteOn.repository.custom.FaqRepositoryCustom;
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
public class FaqRepositoryImpl implements FaqRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QFaq qFaq = QFaq.faq;
    private QMember qMember = QMember.member;

    @Override
    public Page<Faq> searchAllForList(Pageable pageable) {
        List<Faq> faqList = queryFactory
                .select(qFaq)
                .from(qFaq)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qFaq.faqNo.desc())
                .fetch();

        long total = queryFactory.select(qFaq.count()).from(qFaq).fetchOne();

        //페이징처리를 위한 객체 반환
        return new PageImpl<Faq>(faqList, pageable, total);
    }

    @Override
    public Page<Faq> searchAllForCate(FaqPageRequestDTO faqPageRequestDTO, Pageable pageable) {
        String cate1 = faqPageRequestDTO.getCate1();
        String cate2 = faqPageRequestDTO.getCate2();

        //카테고리 선택
        BooleanExpression expression = null;
        if(cate1.equals("member") || cate2.equals("가입")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("member") || cate2.equals("탈퇴")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("member") || cate2.equals("회원정보")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("member") || cate2.equals("로그인")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("event") && cate2.equals("쿠폰/할인혜택")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("event") && cate2.equals("포인트")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("event") && cate2.equals("제휴")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("event") && cate2.equals("이벤트")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("order") && cate2.equals("상품")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("order") && cate2.equals("결제")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("order") && cate2.equals("구매내역")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("order") && cate2.equals("영수증/증빙")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("delivery") && cate2.equals("배송상태/기간")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("delivery") && cate2.equals("배송정보확인/변경")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("delivery") && cate2.equals("해외배송")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("delivery") && cate2.equals("당일배송")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("delivery") && cate2.equals("해외직구")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("cancel") && cate2.equals("반품신청/철회")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("cancel") && cate2.equals("반품정보확인/변경")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("cancel") && cate2.equals("교환AS신청/철회")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("cancel") && cate2.equals("교환정보확인/변경")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("cancel") && cate2.equals("취소신청/철회")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("cancel") && cate2.equals("취소확인/환불정보")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("travel") && cate2.equals("여행/숙박")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("travel") && cate2.equals("항공")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("safe") && cate2.equals("청소년")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("safe") && cate2.equals("서비스")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("safe") && cate2.equals("지식재산권침해")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("safe") && cate2.equals("법령")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("safe") && cate2.equals("게시물")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("safe") && cate2.equals("직거래/외부거래유도")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }else if(cate1.equals("safe") && cate2.equals("표시광고")) {
            expression = qFaq.cate1.eq(cate1).and(qFaq.cate2.eq(cate2));
        }

        List<Faq> faqList = queryFactory
                .select(qFaq)
                .from(qFaq)
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qFaq.faqNo.desc())
                .fetch();

        long total = queryFactory
                .select(qFaq.count())
                .from(qFaq)
                .where(expression)
                .fetchOne();


        //페이징처리를 위한 객체 반환
        return new PageImpl<Faq>(faqList, pageable, total);
    }
}
