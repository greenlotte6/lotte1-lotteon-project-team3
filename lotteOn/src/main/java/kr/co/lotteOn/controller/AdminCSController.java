package kr.co.lotteOn.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.lotteOn.dto.NoticeDTO;
import kr.co.lotteOn.dto.NoticePageRequestDTO;
import kr.co.lotteOn.dto.NoticePageResponseDTO;
import kr.co.lotteOn.service.AdminCSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminCSController {

    private final HttpServletRequest request;
    private final AdminCSService adminCSService;

    /*------------ 관리자 - 고객센터 ------------*/

    //고객센터 - 공지사항
    @GetMapping("/cs/noticeList")
    public String noticeList(Model model, NoticePageRequestDTO pageRequestDTO) {

        //전체 글 조회 서비스 호출
        NoticePageResponseDTO pageResponseDTO = adminCSService.noticeFindAll(pageRequestDTO);

        model.addAttribute("page", pageResponseDTO);
        model.addAttribute("notice", pageResponseDTO.getDtoList());

        return "/admin/cs/noticeList";
    }

    @PostMapping("/cs/noticeList")
    public String noticeWrite(NoticeDTO noticeDTO){
        int no1 = adminCSService.noticeWrite(noticeDTO);

        return "redirect:/admin/cs/noticeList";
    }


    //고객센터 - 자주묻는질문
    @GetMapping("/cs/faqList")
    public String faqList(){
        return "/admin/cs/faqList";
    }

    //고객센터 - 문의하기
    @GetMapping("/cs/qnaList")
    public String qnaList(){
        return "/admin/cs/qnaList";
    }

    //고객센터 - 채용정보
    @GetMapping("/cs/recruitList")
    public String recruitList(){
        return "/admin/cs/recruitList";
    }


}
