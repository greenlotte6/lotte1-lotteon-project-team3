package kr.co.lotteOn.service;

import com.querydsl.core.Tuple;
import kr.co.lotteOn.dto.MemberDTO;
import kr.co.lotteOn.dto.coupon.CouponDTO;
import kr.co.lotteOn.dto.coupon.CouponPageRequestDTO;
import kr.co.lotteOn.dto.coupon.CouponPageResponseDTO;
import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponDTO;
import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponPageRequestDTO;
import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponPageResponseDTO;
import kr.co.lotteOn.dto.qna.QnaDTO;
import kr.co.lotteOn.dto.qna.QnaPageRequestDTO;
import kr.co.lotteOn.dto.qna.QnaPageResponseDTO;
import kr.co.lotteOn.entity.Coupon;
import kr.co.lotteOn.entity.IssuedCoupon;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Qna;
import kr.co.lotteOn.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyPageService {

    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final FaqRepository faqRepository;
    private final IssuedCouponRepository issuedCouponRepository;
    private final QnaRepository qnaRepository;
    private final CouponRepository couponRepository;

    //정보 출력하기
    public MemberDTO getMemberInfo(String memberId) {
        Optional<Member> optMember = memberRepository.findById(memberId);

        if (optMember.isPresent()) {
            Member member = optMember.get();
            return modelMapper.map(member, MemberDTO.class);
        }

        return null;
    }

    //회원별 문의내역
    public QnaPageResponseDTO getQnaByWriter(QnaPageRequestDTO qnaPageRequestDTO) {
        Pageable pageable = qnaPageRequestDTO.getPageable("noticeNo");

        Page<Tuple> pageQna = qnaRepository.searchAllByWriter(qnaPageRequestDTO, pageable);
        log.info("pageQna: {}", pageQna);

        List<QnaDTO> qnaList = pageQna
                .getContent()
                .stream()
                .map(tuple -> {
                    Qna qna = tuple.get(0, Qna.class);
                    String writer = tuple.get(1, String.class);

                    QnaDTO qnaDTO = modelMapper.map(qna, QnaDTO.class);
                    qnaDTO.setWriter(writer);

                    return qnaDTO;
                }).toList();

        log.info("qnaList: {}", qnaList);

        int total = (int) pageQna.getTotalElements();

        return QnaPageResponseDTO
                .builder()
                .pageRequestDTO(qnaPageRequestDTO)
                .dtoList(qnaList)
                .total(total)
                .build();
    }

    //회원별 문의내역 - 글보기
    public QnaDTO findById(int qnaNo){
        Optional<Qna> optQna = qnaRepository.findById(qnaNo);
        if (optQna.isPresent()) {
            Qna qna = optQna.get();

            QnaDTO qnaDTO = modelMapper.map(qna, QnaDTO.class);

            return qnaDTO;
        }

        return null;
    }

    //회원별 쿠폰내역
    public IssuedCouponPageResponseDTO getCouponByWriter(IssuedCouponPageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("issuedNo");

        Page<Tuple> pageCoupon = issuedCouponRepository.searchAllByMemberId(pageRequestDTO, pageable);

        List<IssuedCouponDTO> couponList = pageCoupon
                .getContent()
                .stream()
                .map(tuple -> {
                    IssuedCoupon issuedCoupon = tuple.get(0, IssuedCoupon.class);
                    Coupon coupon = tuple.get(1, Coupon.class);
                    String memberId = tuple.get(2, String.class);

                    IssuedCouponDTO issuedCouponDTO = modelMapper.map(issuedCoupon, IssuedCouponDTO.class);

                    issuedCouponDTO.setCouponCode(coupon.getCouponCode());
                    issuedCouponDTO.setCouponType(coupon.getCouponType());
                    issuedCouponDTO.setCouponName(coupon.getCouponName());
                    issuedCouponDTO.setBenefit(coupon.getBenefit());
                    issuedCouponDTO.setCompanyName(coupon.getCompanyName());
                    issuedCouponDTO.setStartDate(coupon.getStartDate());
                    issuedCouponDTO.setEndDate(coupon.getEndDate());
                    issuedCouponDTO.setEtc(coupon.getEtc());

                    issuedCouponDTO.setMemberId(memberId);

                    return issuedCouponDTO;

                }).collect(Collectors.toList());

        int total = (int) pageCoupon.getTotalElements();

        return IssuedCouponPageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(couponList)
                .total(total)
                .build();
    }

    //회원별 쿠폰내역 - 쿠폰 자세히보기
    public IssuedCouponDTO findCouponById(int issuedNo) {
        Optional<IssuedCoupon> optCoupon = issuedCouponRepository.findById(issuedNo);
        if (optCoupon.isPresent()) {
            IssuedCoupon coupon = optCoupon.get();

            IssuedCouponDTO couponDTO = modelMapper.map(coupon, IssuedCouponDTO.class);

            return couponDTO;
        }
        return null;
    }


}
