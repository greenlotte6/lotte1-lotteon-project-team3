package kr.co.lotteOn.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.lotteOn.dto.faq.FaqDTO;
import kr.co.lotteOn.dto.faq.FaqPageRequestDTO;
import kr.co.lotteOn.dto.faq.FaqPageResponseDTO;
import kr.co.lotteOn.dto.notice.NoticeDTO;
import kr.co.lotteOn.dto.notice.NoticePageRequestDTO;
import kr.co.lotteOn.dto.notice.NoticePageResponseDTO;
import kr.co.lotteOn.service.AdminCSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/cs/delete")
    public String noticeDelete(@ModelAttribute NoticeDTO noticeDTO) {
        adminCSService.noticeDelete(noticeDTO);

        return "redirect:/admin/cs/noticeList";
    }

    /*
    //고객센터 - 공지사항 [다중삭제]
    @PostMapping("/cs/delete-multiple")
    public String noticeDeleteMultiple(@ModelAttribute NoticeDTO noticeDTO) {
        adminCSService.noticeDeleteMultiple(noticeDTO);
        return "redirect:/admin/cs/noticeList";
    }
     */


    /* *********************************공지사항 끝*************************************/


    //고객센터 - 자주묻는질문 [리스트]
    @GetMapping("/cs/faqList")
    public String faqList(Model model, FaqPageRequestDTO pageRequestDTO) {
        FaqPageResponseDTO pageResponseDTO = adminCSService.faqFindAll(pageRequestDTO);

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

    //고객센터 - 자주묻는질문 [카테고리별 리스트, 글작성]

    //고객센터 - 자주묻는질문 [수정]

    //고객센터 - 자주묻는질문 [삭제]


    /* *********************************자주묻는질문 끝*************************************/


    //고객센터 - 문의하기 [리스트]
    @GetMapping("/cs/qnaList")
    public String qnaList(){
        return "/admin/cs/qnaList";
    }

    //고객센터 - 문의하기 [카테고리별 리스트]

    //고객센터 - 문의하기 [리스트,글작성]

    //고객센터 - 문의하기 [카테고리별 리스트, 글작성]

    //고객센터 - 문의하기 [수정]

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
