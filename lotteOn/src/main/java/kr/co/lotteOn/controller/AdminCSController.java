package kr.co.lotteOn.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.lotteOn.dto.faq.FaqDTO;
import kr.co.lotteOn.dto.faq.FaqPageRequestDTO;
import kr.co.lotteOn.dto.faq.FaqPageResponseDTO;
import kr.co.lotteOn.dto.notice.NoticeDTO;
import kr.co.lotteOn.dto.notice.NoticePageRequestDTO;
import kr.co.lotteOn.dto.notice.NoticePageResponseDTO;
import kr.co.lotteOn.dto.qna.QnaDTO;
import kr.co.lotteOn.dto.qna.QnaPageRequestDTO;
import kr.co.lotteOn.dto.qna.QnaPageResponseDTO;
import kr.co.lotteOn.service.AdminCSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminCSController {

    private final HttpServletRequest request;
    private final AdminCSService adminCSService;

    /*------------ 관리자 - 고객센터 ------------*/

    //고객센터 - 공지사항 [리스트]
    @GetMapping("/cs/noticeList")
    public String noticeList(Model model, NoticePageRequestDTO pageRequestDTO) {

        //전체 글 조회 서비스 호출
        NoticePageResponseDTO pageResponseDTO = adminCSService.noticeFindAll(pageRequestDTO);

        model.addAttribute("page", pageResponseDTO);
        model.addAttribute("notice", pageResponseDTO.getDtoList());

        return "/admin/cs/noticeList";
    }

    //고객센터 - 공지사항 [카테고리별 리스트]
    @GetMapping("/cs/noticeSearch")
    public String search(Model model, NoticePageRequestDTO pageRequestDTO) {

        //카테고리별 글 조회
        NoticePageResponseDTO pageResponseDTO = adminCSService.noticeFindAllByCate(pageRequestDTO);

        model.addAttribute("page", pageResponseDTO);

        return "/admin/cs/searchNoticeList";
    }

    //고객센터 - 공지사항 [리스트,글작성]
    @PostMapping("/cs/noticeList")
    public String noticeWrite(NoticeDTO noticeDTO){

        //글 작성
        int no = adminCSService.noticeWrite(noticeDTO);

        return "redirect:/admin/cs/noticeList";
    }

    //고객센터 - 공지사항 [카테고리별 리스트, 글작성]
    @PostMapping("/cs/noticeSearch")
    public String noticeWriteInSearch(NoticeDTO noticeDTO){

        //글 작성
        int no = adminCSService.noticeWrite(noticeDTO);

        return "redirect:/admin/cs/noticeList";
    }

    //고객센터 - 공지사항 [수정]
    @PostMapping("/cs/noticeModify")
    public String noticeModify(@ModelAttribute NoticeDTO noticeDTO){
        System.out.println("Received noticeNo: " + noticeDTO.getNoticeNo());

        adminCSService.noticeModify(noticeDTO);

        return "redirect:/admin/cs/noticeList";
    }

    //고객센터 - 공지사항 [삭제]
    @PostMapping("/cs/noticeDelete")
    public String noticeDelete(@ModelAttribute NoticeDTO noticeDTO) {
        adminCSService.noticeDelete(noticeDTO);

        return "redirect:/admin/cs/noticeList";
    }

    /* *********************************공지사항 끝*************************************/


    //고객센터 - 자주묻는질문 [리스트]
    @GetMapping("/cs/faqList")
    public String faqList(Model model, FaqPageRequestDTO pageRequestDTO) {
        FaqPageResponseDTO pageResponseDTO = adminCSService.faqFindAll(pageRequestDTO);

        // FAQ 리스트에서 1차, 2차 유형 매핑
        List<FaqDTO> faqList = pageResponseDTO.getDtoList();
        for (FaqDTO faq : faqList) {
            // 자동으로 매핑된 1차 및 2차 유형 이름을 설정
            faq.setCate1(faq.getCate1Name());  // 1차 유형 이름
        }

        model.addAttribute("page", pageResponseDTO);
        model.addAttribute("faq", pageResponseDTO.getDtoList());

        return "/admin/cs/faqList";
    }

    //고객센터 - 자주묻는질문 [리스트,글작성]
    @PostMapping("/cs/faqList")
    public String faqWrite(FaqDTO faqDTO){
        int no = adminCSService.faqWrite(faqDTO);

        return "redirect:/admin/cs/faqList";
    }

    //고객센터 - 자주묻는질문 [카테고리별 리스트]
    @GetMapping("/cs/faqSearch")
    public String search(Model model, FaqPageRequestDTO pageRequestDTO) {
        FaqPageResponseDTO pageResponseDTO = adminCSService.faqFindAllByCate(pageRequestDTO);

        // FAQ 리스트에서 1차, 2차 유형 매핑
        List<FaqDTO> faqList = pageResponseDTO.getDtoList();
        for (FaqDTO faq : faqList) {
            // 자동으로 매핑된 1차 및 2차 유형 이름을 설정
            faq.setCate1(faq.getCate1Name());  // 1차 유형 이름
        }

        model.addAttribute("page", pageResponseDTO);

        return "/admin/cs/searchFaqList";
    }

    //고객센터 - 자주묻는질문 [카테고리별 리스트, 글작성]

    //고객센터 - 자주묻는질문 [수정]
    @PostMapping("/cs/faqModify")
    public String faqModify(@ModelAttribute FaqDTO faqDTO){

        adminCSService.faqModify(faqDTO);

        return "redirect:/admin/cs/faqList";
    }

    //고객센터 - 자주묻는질문 [삭제]
    @PostMapping("/cs/faqDelete")
    public String noticeDelete(@ModelAttribute FaqDTO faqDTO){

        adminCSService.faqDelete(faqDTO);

        return "redirect:/admin/cs/faqList";
    }


    /* *********************************자주묻는질문 끝*************************************/


    //고객센터 - 문의하기 [리스트]
    @GetMapping("/cs/qnaList")
    public String qnaList(Model model, QnaPageRequestDTO pageRequestDTO) {
        QnaPageResponseDTO pageResponseDTO = adminCSService.qnaFindAll(pageRequestDTO);

        List<QnaDTO> qnaList = pageResponseDTO.getDtoList();
        for (QnaDTO qna : qnaList) {

            // 자동으로 매핑된 1차 및 2차 유형 이름을 설정
            qna.setCate1(qna.getCate1Name());
        }

        model.addAttribute("page", pageResponseDTO);
        model.addAttribute("qna", pageResponseDTO.getDtoList());

        return "/admin/cs/qnaList";
    }

    //고객센터 - 문의하기 [카테고리별 리스트]
    @GetMapping("/cs/qnaSearch")
    public String search(Model model, QnaPageRequestDTO pageRequestDTO) {
        QnaPageResponseDTO pageResponseDTO = adminCSService.qnaFindAllByCate(pageRequestDTO);
        List<QnaDTO> qnaList = pageResponseDTO.getDtoList();
        for (QnaDTO qna : qnaList) {
            qna.setCate1(qna.getCate1Name());
        }

        model.addAttribute("page", pageResponseDTO);

        return "/admin/cs/searchQnaList";
    }

    //고객센터 - 문의하기 [리스트,글작성]

    //고객센터 - 문의하기 [카테고리별 리스트, 글작성]

    //고객센터 - 문의하기 [수정]
    @PostMapping("/cs/qnaModify")
    public String qnaModify(@ModelAttribute QnaDTO qnaDTO){
        String cate1Eng = qnaDTO.getCate1NameBack();

        System.out.println("QnaDTO: " + qnaDTO);
        System.out.println("QnaDTO: " + qnaDTO);
        System.out.println("QnaDTO: " + qnaDTO);
        System.out.println("QnaDTO: " + qnaDTO);
        System.out.println("QnaDTO: " + qnaDTO);
        System.out.println("QnaDTO: " + qnaDTO);


        //영어로 다시변경
        qnaDTO.setCate1(cate1Eng);
        
        adminCSService.qnaModify(qnaDTO);

        return "redirect:/admin/cs/qnaList";
    }


    //고객센터 - 문의하기 [삭제]

    /* *********************************문의하기 끝*************************************/


    //고객센터 - 채용정보 [리스트]
    @GetMapping("/cs/recruitList")
    public String recruitList(){
        return "/admin/cs/recruitList";
    }


    //고객센터 - 채용정보 [카테고리별 리스트]

    //고객센터 - 채용정보 [리스트,글작성]

    //고객센터 - 채용정보 [카테고리별 리스트, 글작성]

    //고객센터 - 채용정보 [수정]

    //고객센터 - 채용정보 [삭제]


    /* *********************************채용정보 끝*************************************/



}
