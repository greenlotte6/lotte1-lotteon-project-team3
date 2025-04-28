package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.faq.FaqDTO;
import kr.co.lotteOn.dto.faq.FaqPageRequestDTO;
import kr.co.lotteOn.dto.faq.FaqPageResponseDTO;
import kr.co.lotteOn.dto.notice.NoticeDTO;
import kr.co.lotteOn.dto.notice.NoticePageRequestDTO;
import kr.co.lotteOn.dto.notice.NoticePageResponseDTO;
import kr.co.lotteOn.dto.qna.QnaDTO;
import kr.co.lotteOn.dto.qna.QnaPageRequestDTO;
import kr.co.lotteOn.dto.qna.QnaPageResponseDTO;
import kr.co.lotteOn.entity.Faq;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Notice;
import kr.co.lotteOn.entity.Qna;
import kr.co.lotteOn.repository.FaqRepository;
import kr.co.lotteOn.repository.NoticeRepository;
import kr.co.lotteOn.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustService {

    private final NoticeRepository noticeRepository;
    private final QnaRepository qnaRepository;
    private final FaqRepository faqRepository;
    private final ModelMapper modelMapper;

    //공지사항 전체 - 글보기
    public NoticeDTO findById(int noticeNo){
        Optional<Notice> optNotice = noticeRepository.findById(noticeNo);
        if(optNotice.isPresent()){
            Notice notice = optNotice.get();
            NoticeDTO noticeDTO = modelMapper.map(notice, NoticeDTO.class);

            return noticeDTO;
        }

        return null;
    }

    //공지사항 - 카테고리별 리스트
    public NoticePageResponseDTO noticeFindAllByCate(NoticePageRequestDTO pageRequestDTO, String cate){
        Pageable pageable = pageRequestDTO.getPageable("noticeNo");

        Page<Notice> pageNotice = noticeRepository.findAllByCate(cate, pageable);

        List<NoticeDTO> noticeList = pageNotice
                .getContent()
                .stream()
                .map(notice -> {
                    NoticeDTO noticeDTO = modelMapper.map(notice, NoticeDTO.class);

                    noticeDTO.setCate(notice.getCate());
                    noticeDTO.setWriter(notice.getWriter().getId());

                    return noticeDTO;
                }).toList();

        int total = (int) pageNotice.getTotalElements();

        return NoticePageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(noticeList)
                .total(total)
                .build();
        
    }

    /* **************************공지사항 끝******************************/

    //문의하기 - 글보기
    public QnaDTO findQnaById(int qnaNo){
        Optional<Qna> optQna = qnaRepository.findById(qnaNo);
        if(optQna.isPresent()){
            Qna qna = optQna.get();
            QnaDTO qnaDTO = modelMapper.map(qna, QnaDTO.class);
            qnaDTO.setWriter(qna.getWriter().getId());

            return qnaDTO;
        }

        return null;
    }

    //문의하기 - 카테고리별 리스트
    public QnaPageResponseDTO qnaFindAllByCate1(QnaPageRequestDTO pageRequestDTO, String cate1){
        Pageable pageable = pageRequestDTO.getPageable("qnaNo");
        Page<Qna> pageQna = qnaRepository.findAllByCate1(cate1, pageable);

        List<QnaDTO> qnaList = pageQna
                .getContent()
                .stream()
                .map(qna -> {
                    QnaDTO qnaDTO = modelMapper.map(qna, QnaDTO.class);

                    qnaDTO.setCate1(qna.getCate1());
                    qnaDTO.setWriter(qna.getWriter().getId());

                    return qnaDTO;

                }).toList();

        int total = (int) pageQna.getTotalElements();

        return QnaPageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(qnaList)
                .total(total)
                .build();

    }

    //문의하기 - 글쓰기
    public int qnaWrite(QnaDTO qnaDTO){
        Member member = Member.builder()
                .id(qnaDTO.getWriter())
                .build();

        Qna qna = modelMapper.map(qnaDTO, Qna.class);
        qna.setWriter(member);

        Qna savedQna = qnaRepository.save(qna);

        return savedQna.getQnaNo();
    }

    /* **************************문의하기 끝******************************/

    public FaqPageResponseDTO faqFindAllByCate1(FaqPageRequestDTO pageRequestDTO, String cate1) {
        // 기존 Pageable을 그대로 사용하지 않고, 이 메소드 내에서 새로 설정
        Pageable pageable = PageRequest.of(0, 1000, Sort.by("faqNo").descending());  // 1000개까지 한 번에 조회

        // cate1에 해당하는 FAQ를 조회
        Page<Faq> pageFaq = faqRepository.findAllByCate1(cate1, pageable);

        // 조회된 FAQ 데이터를 DTO로 변환
        List<FaqDTO> faqList = pageFaq.getContent()
                .stream()
                .map(faq -> {
                    FaqDTO faqDTO = modelMapper.map(faq, FaqDTO.class);
                    faqDTO.setCate1(faq.getCate1());
                    faqDTO.setCate2(faq.getCate2());
                    faqDTO.setWriter(faq.getWriter().getId());
                    return faqDTO;
                })
                .toList();

        // cate1/cate2 복합 키로 그룹화
        Map<String, List<FaqDTO>> groupedByCate1AndCate2 = faqList.stream()
                .collect(Collectors.groupingBy(faqDTO -> faqDTO.getCate1() + "/" + faqDTO.getCate2()));

        // 총 데이터 개수 계산
        int total = (int) pageFaq.getTotalElements();

        // FaqPageResponseDTO 빌더로 객체 생성
        FaqPageResponseDTO responseDTO = FaqPageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(faqList)
                .total(total)
                .build();

        // 그룹화된 데이터 추가 주입
        responseDTO.setGroupedByCate2(groupedByCate1AndCate2);

        return responseDTO;
    }

    public FaqDTO findFaqById(int faqNo) {
        Optional<Faq> optFaq = faqRepository.findById(faqNo);
        if(optFaq.isPresent()){
            Faq faq = optFaq.get();
            FaqDTO faqDTO = modelMapper.map(faq, FaqDTO.class);
            faqDTO.setWriter(faq.getWriter().getId());

            return faqDTO;
        }

        return null;
    }








    /* **************************자주묻는질문 끝******************************/


}
