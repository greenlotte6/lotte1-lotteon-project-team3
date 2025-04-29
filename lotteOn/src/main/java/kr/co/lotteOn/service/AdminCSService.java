package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.story.StoryDTO;
import kr.co.lotteOn.dto.faq.FaqDTO;
import kr.co.lotteOn.dto.faq.FaqPageRequestDTO;
import kr.co.lotteOn.dto.faq.FaqPageResponseDTO;
import kr.co.lotteOn.dto.notice.NoticeDTO;
import kr.co.lotteOn.dto.notice.NoticePageRequestDTO;
import kr.co.lotteOn.dto.notice.NoticePageResponseDTO;
import kr.co.lotteOn.dto.qna.QnaDTO;
import kr.co.lotteOn.dto.qna.QnaPageRequestDTO;
import kr.co.lotteOn.dto.qna.QnaPageResponseDTO;
import kr.co.lotteOn.dto.recruit.RecruitDTO;
import kr.co.lotteOn.dto.recruit.RecruitPageRequestDTO;
import kr.co.lotteOn.dto.recruit.RecruitPageResponseDTO;
import kr.co.lotteOn.dto.story.StoryPageRequestDTO;
import kr.co.lotteOn.dto.story.StoryPageResponseDTO;
import kr.co.lotteOn.entity.*;
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
    private final QnaRepository qnaRepository;
    private final RecruitRepository recruitRepository;
    private final NoticeRepository noticeRepository;
    private final FaqRepository faqRepository;
    private final StoryRepository storyRepository;

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

    /*****************************************공지사항 끝***********************************/

    /*자주묻는질문 - 글 리스트 출력하기*/
    public FaqPageResponseDTO faqFindAll(FaqPageRequestDTO faqPageRequestDTO) {
        Pageable pageable = faqPageRequestDTO.getPageable("faqNo");

        Page<Faq> pageFaq = faqRepository.searchAllForList(pageable);
        log.info("pageFaq: {}", pageFaq);

        //Entity -> DTO
        List<FaqDTO> faqList = pageFaq
                .getContent()
                .stream()
                .map(faq -> {
                    FaqDTO faqDTO = modelMapper.map(faq, FaqDTO.class);
                    faqDTO.setWriter(faq.getWriter().getId());

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
    public FaqPageResponseDTO faqFindAllByCate(FaqPageRequestDTO faqPageRequestDTO) {
        Pageable pageable = faqPageRequestDTO.getPageable("faqNo");
        Page<Faq> pageFaq = faqRepository.searchAllForCate(faqPageRequestDTO, pageable);
        log.info("pageFaq: {}", pageFaq);

        List<FaqDTO> faqList = pageFaq
                .getContent()
                .stream()
                .map(faq -> {
                    FaqDTO faqDTO = modelMapper.map(faq, FaqDTO.class);
                    faqDTO.setWriter(faq.getWriter().getId());

                    return faqDTO;
                })
                .toList();
        int total = (int) pageFaq.getTotalElements();

        return FaqPageResponseDTO
                .builder()
                .pageRequestDTO(faqPageRequestDTO)
                .dtoList(faqList)
                .total(total)
                .build();
    }

    /*자주묻는질문 - 글 수정하기(글찾기)*/
    public FaqDTO faqFindById(int faqNo){
        Optional<Faq> optFaq = faqRepository.findById(faqNo);
        if(optFaq.isPresent()){
            Faq faq = optFaq.get();
            FaqDTO faqDTO = modelMapper.map(faq, FaqDTO.class);
            return faqDTO;
        }
        return null;
    }

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
    public void faqModify(FaqDTO faqDTO) {
        Faq faq = faqRepository.findById(faqDTO.getFaqNo()).get();
        faq.setCate1(faqDTO.getCate1());
        faq.setCate2(faqDTO.getCate2());
        faq.setTitle(faqDTO.getTitle());
        faq.setContent(faqDTO.getContent());

        faqRepository.save(faq);
    }

    /*자주묻는질문 - 글 삭제하기*/

    @Transactional
    public void faqDelete(FaqDTO faqDTO) {
        Faq faq = faqRepository.findById(faqDTO.getFaqNo()).get();
        faqRepository.delete(faq);
    }

    /*****************************************자주묻는질문 끝***********************************/

    /*문의하기 - 글 리스트 출력하기*/
    public QnaPageResponseDTO qnaFindAll(QnaPageRequestDTO qnaPageRequestDTO) {
        Pageable pageable = qnaPageRequestDTO.getPageable("qnaNo");

        Page<Qna> pageQna = qnaRepository.searchAllForList(pageable);

        List<QnaDTO> qnaList = pageQna
                .getContent()
                .stream()
                .map(qna -> {
                    QnaDTO qnaDTO = modelMapper.map(qna, QnaDTO.class);
                    qnaDTO.setWriter(qna.getWriter().getId());

                    return qnaDTO;
                })
                .toList();

        int total = (int) pageQna.getTotalElements();

        return QnaPageResponseDTO
                .builder()
                .pageRequestDTO(qnaPageRequestDTO)
                .dtoList(qnaList)
                .total(total)
                .build();
    }


    /*문의하기 - (검색)글 리스트 출력하기*/
    public QnaPageResponseDTO qnaFindAllByCate(QnaPageRequestDTO qnaPageRequestDTO) {
        Pageable pageable = qnaPageRequestDTO.getPageable("qnaNo");
        Page<Qna> pageQna = qnaRepository.searchAllForCate(qnaPageRequestDTO, pageable);
        log.info("pageQna: {}", pageQna);

        List<QnaDTO> qnaList = pageQna
                .getContent()
                .stream()
                .map(qna -> {
                    QnaDTO qnaDTO = modelMapper.map(qna, QnaDTO.class);
                    qnaDTO.setWriter(qna.getWriter().getId());

                    return qnaDTO;
                }).toList();

        int total = (int) pageQna.getTotalElements();

        return QnaPageResponseDTO
                .builder()
                .pageRequestDTO(qnaPageRequestDTO)
                .dtoList(qnaList)
                .total(total)
                .build();
    }

    /*문의하기 - 글 수정하기(글찾기)*/
    public QnaDTO qnaFindById(int qnaNo){
        Optional<Qna> optQna = qnaRepository.findById(qnaNo);
        if(optQna.isPresent()){
            Qna qna = optQna.get();
            QnaDTO qnaDTO = modelMapper.map(qna, QnaDTO.class);
            return qnaDTO;
        }
        return null;
    }

    /*문의하기 - 글 답변하기*/
    public void qnaModify(QnaDTO qnaDTO){
        Qna qna = qnaRepository.findById(qnaDTO.getQnaNo()).get();
        qna.setCate1(qnaDTO.getCate1());
        qna.setCate2(qnaDTO.getCate2());
        qna.setTitle(qnaDTO.getTitle());
        qna.setContent(qnaDTO.getContent());
        qna.setComment(qnaDTO.getComment());

        qnaRepository.save(qna);
    }

    /*문의하기 - 글 삭제하기*/
    @Transactional
    public void qnaDelete(QnaDTO qnaDTO) {
        Qna qna = qnaRepository.findById(qnaDTO.getQnaNo()).get();
        qnaRepository.delete(qna);
    }

    /* ****************************************문의하기 끝***********************************/

    /*채용 - 글 리스트 출력하기*/
    public RecruitPageResponseDTO recruitFindAll(RecruitPageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("recruitNo");

        Page<Recruit> pageRecruit = recruitRepository.searchAllForList(pageable);

        List<RecruitDTO> recruitList = pageRecruit
                .getContent()
                .stream()
                .map(notice -> {
                    RecruitDTO noticeDTO = modelMapper.map(notice, RecruitDTO.class);
                    noticeDTO.setWriter(notice.getWriter().getId());

                    return noticeDTO;
                }).toList();

        int total = (int) pageRecruit.getTotalElements();

        return RecruitPageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(recruitList)
                .total(total)
                .build();
    }

    /*채용 - (검색)글 리스트 출력하기*/
    public RecruitPageResponseDTO recruitSearchAll(RecruitPageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("recruitNo");

        Page<Recruit> pageRecruit = recruitRepository.searchAllForCate(pageRequestDTO, pageable);

        List<RecruitDTO> recruitDTOList = pageRecruit
                .getContent()
                .stream()
                .map(recruit -> {
                    RecruitDTO recruitDTO = modelMapper.map(recruit, RecruitDTO.class);
                    recruitDTO.setWriter(recruit.getWriter().getId());
                    return recruitDTO;
                }).toList();
        int total = (int) pageRecruit.getTotalElements();

        return RecruitPageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(recruitDTOList)
                .total(total)
                .build();
    }


    /*채용 - 글 작성하기*/
    public int recruitWrite(RecruitDTO recruitDTO){
        // 1. 작성자 설정
        Member member = Member.builder()
                .id(recruitDTO.getWriter())
                .build();

        // 2. DTO → Entity 변환
        Recruit recruit = modelMapper.map(recruitDTO, Recruit.class);

        // 3. 작성자 설정
        recruit.setWriter(member);

        // 4. 저장 (한 번만)
        Recruit savedRecruit = recruitRepository.save(recruit);

        // 5. 리턴
        return savedRecruit.getRecruitNo();
    }


    /*채용 - 글 삭제하기*/
    @Transactional
    public void recruitDelete(RecruitDTO recruitDTO){
        Recruit recruit = recruitRepository.findById(recruitDTO.getRecruitNo()).get();
        recruitRepository.delete(recruit);
    }


    /*****************************************채용 끝***********************************/

    //소식과 이야기 - 글 출력하기
    public StoryPageResponseDTO storyFindAll(StoryPageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("storyNo");
        Page<Story> pageStory = storyRepository.searchAllForList(pageable);

        List<StoryDTO> storyList = pageStory
                .getContent()
                .stream()
                .map(story -> {
                    StoryDTO storyDTO = modelMapper.map(story, StoryDTO.class);

                    storyDTO.setWriter(story.getWriter().getId());

                    return storyDTO;
                }).toList();

        int total = (int) pageStory.getTotalElements();
        return StoryPageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(storyList)
                .total(total)
                .build();

    }

    //소식과 이야기 - (검색)리스트
    public StoryPageResponseDTO storySearchAll(StoryPageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("storyNo");
        Page<Story> pageStory = storyRepository.searchAllForCate(pageRequestDTO, pageable);

        List<StoryDTO> storyList = pageStory
                .getContent()
                .stream()
                .map(story -> {
                    StoryDTO storyDTO = modelMapper.map(story, StoryDTO.class);
                    storyDTO.setWriter(story.getWriter().getId());

                    return storyDTO;
                }).toList();
        int total = (int) pageStory.getTotalElements();

        return StoryPageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(storyList)
                .total(total)
                .build();
    }

    //소식과 이야기 - 글 등록하기
    public Story saveStory(StoryDTO storyDTO){

        Member member = Member.builder()
                .id(storyDTO.getWriter())
                .build();

        Story story = modelMapper.map(storyDTO, Story.class);
        story.setWriter(member);

        story.setImageMain(storyDTO.getImageMainFile().getOriginalFilename());

        Story storySaved = storyRepository.save(story);

        return storySaved;
    }

    //소식과 이야기 - 글 삭제하기
    @Transactional
    public void storyDelete(StoryDTO storyDTO){
        Story story = storyRepository.findById(storyDTO.getStoryNo()).get();
        storyRepository.delete(story);
    }

}
