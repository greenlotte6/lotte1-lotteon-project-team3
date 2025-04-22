package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.faq.FaqDTO;
import kr.co.lotteOn.dto.faq.FaqPageRequestDTO;
import kr.co.lotteOn.dto.faq.FaqPageResponseDTO;
import kr.co.lotteOn.dto.notice.NoticeDTO;
import kr.co.lotteOn.dto.notice.NoticePageRequestDTO;
import kr.co.lotteOn.dto.notice.NoticePageResponseDTO;
import kr.co.lotteOn.dto.qna.QnaDTO;
import kr.co.lotteOn.dto.recruit.RecruitDTO;
import kr.co.lotteOn.entity.Faq;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Notice;
import kr.co.lotteOn.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminCSService {
    
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    //private final QnaRepository qnaRepository;
    //private final RecruitRepository recruitRepository;
    private final NoticeRepository noticeRepository;
    private final FaqRepository faqRepository;

    /*공지사항 - 글 리스트 출력하기*/
    public NoticePageResponseDTO noticeFindAll(NoticePageRequestDTO pageRequestDTO) {
        //페이징 처리 위한 pageable 객체 생성
        Pageable pageable = pageRequestDTO.getPageable("noticeNo");

        Page<Notice> pageNotice = noticeRepository.searchAllForList(pageable);
        log.info("pageNotice: {}", pageNotice);

        // Notice Entity → NoticeDTO
        List<NoticeDTO> noticeList = pageNotice
                .getContent()
                .stream()
                .map(notice -> {
                    // Notice를 NoticeDTO로 변환
                    NoticeDTO noticeDTO = modelMapper.map(notice, NoticeDTO.class);

                    // writer 필드를 String으로 세팅 (Notice의 writer가 Member이므로)
                    // 필요시, MemberDTO를 사용하여 더 많은 정보 추가 가능
                    noticeDTO.setWriter(notice.getWriter().getId()); // 여기서 member의 id를 가져옴



                    return noticeDTO;
                })
                .toList();

        int total = (int) pageNotice.getTotalElements();

        return NoticePageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(noticeList)
                .total(total)
                .build();
    }


    /*공지사항 - (검색)글 리스트 출력하기*/
    public NoticePageResponseDTO noticeFindAllByCate(NoticePageRequestDTO noticePageRequestDTO) {
        //페이징
        Pageable pageable = noticePageRequestDTO.getPageable("noticeNo");

        Page<Notice> pageNotice = noticeRepository.searchAllForCate(noticePageRequestDTO, pageable);
        log.info("pageNotice: {}", pageNotice);

        //Entity -> DTO
        List<NoticeDTO> noticeList = pageNotice
                .getContent()
                .stream()
                .map(notice -> {
                    // Notice를 NoticeDTO로 변환
                    NoticeDTO noticeDTO = modelMapper.map(notice, NoticeDTO.class);

                    // writer 필드를 String으로 세팅 (Notice의 writer가 Member이므로)
                    // 필요시, MemberDTO를 사용하여 더 많은 정보 추가 가능
                    noticeDTO.setWriter(notice.getWriter().getId()); // 여기서 member의 id를 가져옴



                    return noticeDTO;
                })
                .toList();

        int total = (int) pageNotice.getTotalElements();

        return NoticePageResponseDTO
                .builder()
                .pageRequestDTO(noticePageRequestDTO)
                .dtoList(noticeList)
                .total(total)
                .build();
    }




    /*공지사항 - 글 작성하기*/
    public int noticeWrite(NoticeDTO noticeDTO){

        Member member = Member.builder()
                .id(noticeDTO.getWriter())
                .build();

        Notice notice = modelMapper.map(noticeDTO, Notice.class);
        notice.setWriter(member);

        log.info("notice : {}", notice);

        //JPA 저장
        Notice savedNotice = noticeRepository.save(notice);
        log.info("savedNotice : {}", savedNotice);

        noticeRepository.save(notice);

        return savedNotice.getNoticeNo();
        
    }

    /*공지사항 - 글 수정하기(글찾기)*/
    public NoticeDTO noticeFindById(int noticeNo){
        Optional<Notice> optNotice = noticeRepository.findById(noticeNo);
        if(optNotice.isPresent()){
            Notice notice = optNotice.get();

            NoticeDTO noticeDTO = modelMapper.map(notice, NoticeDTO.class);

            return noticeDTO;
        }

        return null;
    }

    /*공지사항 - 글 수정하기*/
    public void noticeModify(NoticeDTO noticeDTO) {
        Notice notice = noticeRepository.findById(noticeDTO.getNoticeNo()).get();

        notice.setCate(noticeDTO.getCate());
        notice.setTitle(noticeDTO.getTitle());
        notice.setContent(noticeDTO.getContent());

        noticeRepository.save(notice);
    }

    /*공지사항 - 글 삭제하기*/
    @Transactional
    public void noticeDelete(NoticeDTO noticeDTO) {
        Notice notice = noticeRepository.findById(noticeDTO.getNoticeNo()).get();

        noticeRepository.delete(notice);
    }

    /*
    @Transactional
    public void noticeDeleteMultiple(NoticeDTO noticeDTO) {
        List<Integer> noticeNoList = noticeDTO.getNoticeNoList();
        if(noticeNoList != null && !noticeNoList.isEmpty()){
            List<Notice> notices = noticeRepository.findAllById(noticeNoList);
            noticeRepository.deleteAll(notices);
        }

    }
     */

    /*****************************************공지사항 끝***********************************/

    /*자주묻는질문 - 글 리스트 출력하기*/
    public FaqPageResponseDTO faqFindAll(FaqPageRequestDTO faqPageRequestDTO) {
        Pageable pageable = faqPageRequestDTO.getPageable("faqNo");

        Page<Faq> pageFaq = faqRepository.searchAllForList(pageable);
        log.info("pageFaq: {}", pageFaq);

        List<FaqDTO> faqList = pageFaq
                .getContent()
                .stream()
                .map(faq -> {
                    FaqDTO faqDTO = modelMapper.map(faq, FaqDTO.class);

                    return faqDTO;
                }).toList();

        int total = (int) pageFaq.getTotalElements();

        return FaqPageResponseDTO
                .builder()
                .pageRequestDTO(faqPageRequestDTO)
                .dtoList(faqList)
                .total(total)
                .build();
    }


    /*자주묻는질문 - (검색)글 리스트 출력하기*/

    /*자주묻는질문 - 글 수정하기(글찾기*/

    /*자주묻는질문 - 글 작성하기*/
    public int faqWrite(FaqDTO faqDTO){
        Member member = Member.builder()
                .id(faqDTO.getWriter())
                .build();

        Faq faq = modelMapper.map(faqDTO, Faq.class);
        faq.setWriter(member);

        Faq savedFaq = faqRepository.save(faq);
        log.info("savedFaq : {}", savedFaq);

        faqRepository.save(faq);

        return savedFaq.getFaqNo();
    }

    /*자주묻는질문 - 글 수정하기*/

    /*자주묻는질문 - 글 삭제하기*/

    /*****************************************자주묻는질문 끝***********************************/

    /*문의하기 - 글 작성하기*/
    public int qnaWrite(QnaDTO qnaDTO){

        return 0;
    }

    /*****************************************문의하기 끝***********************************/

    /*채용 - 글 작성하기*/
    public int recruitWrite(RecruitDTO recruitDTO){

        return 0;
    }

    /*****************************************채용 끝***********************************/



}
