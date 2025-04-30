package kr.co.lotteOn.service;

import com.querydsl.core.Tuple;
import kr.co.lotteOn.dto.MemberDTO;
import kr.co.lotteOn.dto.qna.QnaDTO;
import kr.co.lotteOn.dto.qna.QnaPageRequestDTO;
import kr.co.lotteOn.dto.qna.QnaPageResponseDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Qna;
import kr.co.lotteOn.repository.FaqRepository;
import kr.co.lotteOn.repository.IssuedCouponRepository;
import kr.co.lotteOn.repository.MemberRepository;
import kr.co.lotteOn.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyPageService {

    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final FaqRepository faqRepository;
    private final IssuedCouponRepository issuedCouponRepository;
    private final QnaRepository qnaRepository;

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

    public QnaPageResponseDTO getAll(QnaPageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("qnaNo");
        Page<Qna> pageQnas = qnaRepository.findAll(pageable);
        log.info("pageQnas: {}", pageQnas);

        List<QnaDTO> qnaList = pageQnas
                .getContent()
                .stream()
                .map(qna -> modelMapper.map(qna, QnaDTO.class)).toList();
        log.info("qnaList: {}", qnaList);
        int total = (int) pageQnas.getTotalElements();

        return QnaPageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(qnaList)
                .total(total)
                .build();
    }

}
