package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.FaqDTO;
import kr.co.lotteOn.dto.NoticeDTO;
import kr.co.lotteOn.dto.QnaDTO;
import kr.co.lotteOn.dto.RecruitDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Notice;
import kr.co.lotteOn.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void findAll(){

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

        return 0;
        
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
