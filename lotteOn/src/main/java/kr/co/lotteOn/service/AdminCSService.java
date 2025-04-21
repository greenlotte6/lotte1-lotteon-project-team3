package kr.co.lotteOn.service;

import com.querydsl.core.Tuple;
import kr.co.lotteOn.dto.*;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Notice;
import kr.co.lotteOn.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    /*글 리스트 출력하기*/
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


    /*(검색)글 리스트 출력하기*/
    public void searchAll(){

    }




    /*글 작성하기*/

    //공지사항
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

    //자주묻는질문
    public int faqWrite(FaqDTO faqDTO){

        return 0;
    }

    //문의하기
    public int qnaWrite(QnaDTO qnaDTO){

        return 0;
    }

    //채용
    public int recruitWrite(RecruitDTO recruitDTO){

        return 0;
    }



    /*글 수정하기*/
    public void modify(){
    }


    /*글 삭제하기*/
    public void delete(){

    }


    /*글 조회하기*/
    public void view(){

    }
    
}
