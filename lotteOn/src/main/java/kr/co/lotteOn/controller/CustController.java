package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.faq.FaqDTO;
import kr.co.lotteOn.dto.faq.FaqPageRequestDTO;
import kr.co.lotteOn.dto.faq.FaqPageResponseDTO;
import kr.co.lotteOn.dto.notice.NoticeDTO;
import kr.co.lotteOn.dto.notice.NoticePageRequestDTO;
import kr.co.lotteOn.dto.notice.NoticePageResponseDTO;
import kr.co.lotteOn.dto.qna.QnaDTO;
import kr.co.lotteOn.dto.qna.QnaPageRequestDTO;
import kr.co.lotteOn.dto.qna.QnaPageResponseDTO;
import kr.co.lotteOn.repository.NoticeRepository;
import kr.co.lotteOn.service.AdminCSService;
import kr.co.lotteOn.service.AdminLotteService;
import kr.co.lotteOn.service.CustService;
import kr.co.lotteOn.service.TermsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cust")
@Controller
public class CustController {

    private final TermsService termsService;
    private final AdminCSService adminCSService;
    private final AdminLotteService adminLotteService;
    private final NoticeRepository noticeRepository;
    private final CustService custService;

    //고객센터 - 메인
    @GetMapping("/cust_main")
    public String cust_main(Model model) {
        List<NoticeDTO> notices = adminLotteService.findAllNoticeByLimit5();
        List<QnaDTO> qnas = adminLotteService.findAllQnaByLimit5();

        for (QnaDTO qna : qnas) {

            // 자동으로 매핑된 1차 및 2차 유형 이름을 설정
            qna.setCate1(qna.getCate1Name());
        }

        model.addAttribute("notices", notices);
        model.addAttribute("qnas", qnas);

        return "/cust/cust_main";
    }
    /* **************************고객센터 메인 끝*********************************** */

    //공지사항 - 메인(전체)
    @GetMapping("/cust_notice_main")
    public String notice_main(Model model, NoticePageRequestDTO pageRequestDTO) {
        NoticePageResponseDTO pageResponseDTO = adminCSService.noticeFindAll(pageRequestDTO);

        model.addAttribute("notice", pageResponseDTO.getDtoList());
        model.addAttribute("page", pageResponseDTO);

        return "/cust/cust_notice_main";
    }

    //공지사항 - 카테고리별 리스트
    @GetMapping("/cust_notice")
    public String notice_service(Model model, NoticePageRequestDTO pageRequestDTO,@RequestParam(name = "cate", required = true) String cate){
        NoticePageResponseDTO pageResponseDTO = custService.noticeFindAllByCate(pageRequestDTO, cate);

        model.addAttribute("article", pageResponseDTO.getDtoList());
        model.addAttribute("page", pageResponseDTO);
        model.addAttribute("cate", cate);

        return "/cust/cust_notice";
    }

    //공지사항 - 글보기
    @GetMapping("/cust_notice_view")
    public String notice_view(int noticeNo, Model model) {
        //글조회
        NoticeDTO noticeDTO = custService.findById(noticeNo);

        model.addAttribute("notice", noticeDTO);

        return "/cust/cust_notice_view";
    }

    /* ***************************공지사항 끝*************************** */
    // 자주 묻는 질문 - 배송
    @GetMapping("/cust_faq_deliver")
    public String faq_deliver(Model model, FaqPageRequestDTO pageRequestDTO) {
        String cate1 = "delivery";  // cate1은 "delivery"로 고정

        // 서비스에서 cate2별로 그룹화된 FAQ 항목 가져오기
        FaqPageResponseDTO pageResponseDTO = custService.faqFindAllByCate1(pageRequestDTO, cate1);

        // 그룹화된 데이터를 모델에 전달
        model.addAttribute("faq", pageResponseDTO.getDtoList());  // 전체 FAQ 리스트
        model.addAttribute("groupedByCate2", pageResponseDTO.getGroupedByCate2());  // cate2별로 그룹화된 FAQ 데이터
        model.addAttribute("page", pageResponseDTO);  // 페이징 처리 정보

        return "/cust/cust_faq_deliver";
    }


    // 자주 묻는 질문 - 쿠폰/이벤트
    @GetMapping("/cust_faq_event")
    public String faq_event(Model model, FaqPageRequestDTO pageRequestDTO) {
        String cate1 = "event";  // cate1은 "event"로 고정

        // 서비스에서 cate2별로 그룹화된 FAQ 항목 가져오기
        FaqPageResponseDTO pageResponseDTO = custService.faqFindAllByCate1(pageRequestDTO, cate1);

        // 그룹화된 데이터를 모델에 전달
        model.addAttribute("faq", pageResponseDTO.getDtoList());  // 전체 FAQ 리스트
        model.addAttribute("groupedByCate2", pageResponseDTO.getGroupedByCate2());  // cate2별로 그룹화된 FAQ 데이터
        model.addAttribute("page", pageResponseDTO);  // 페이징 처리 정보

        return "/cust/cust_faq_event";
    }

    // 자주 묻는 질문 - 주문/결제
    @GetMapping("/cust_faq_order")
    public String faq_order(Model model, FaqPageRequestDTO pageRequestDTO) {
        String cate1 = "order";  // cate1은 "order_payment"로 고정

        // 서비스에서 cate2별로 그룹화된 FAQ 항목 가져오기
        FaqPageResponseDTO pageResponseDTO = custService.faqFindAllByCate1(pageRequestDTO, cate1);

        // 그룹화된 데이터를 모델에 전달
        model.addAttribute("faq", pageResponseDTO.getDtoList());  // 전체 FAQ 리스트
        model.addAttribute("groupedByCate2", pageResponseDTO.getGroupedByCate2());  // cate2별로 그룹화된 FAQ 데이터
        model.addAttribute("page", pageResponseDTO);  // 페이징 처리 정보

        return "/cust/cust_faq_order";
    }

    // 자주 묻는 질문 - 취소/반품/교환
    @GetMapping("/cust_faq_cancel")
    public String faq_refund(Model model, FaqPageRequestDTO pageRequestDTO) {
        String cate1 = "cancel";  // cate1은 "cancel"로 고정

        log.info("cate1 : {}", cate1);
        // 서비스에서 cate2별로 그룹화된 FAQ 항목 가져오기
        FaqPageResponseDTO pageResponseDTO = custService.faqFindAllByCate1(pageRequestDTO, cate1);

        log.info("pageResponseDTO : {}", pageResponseDTO);

        // 그룹화된 데이터를 모델에 전달
        model.addAttribute("faq", pageResponseDTO.getDtoList());  // 전체 FAQ 리스트
        model.addAttribute("groupedByCate2", pageResponseDTO.getGroupedByCate2());  // cate2별로 그룹화된 FAQ 데이터
        model.addAttribute("page", pageResponseDTO);  // 페이징 처리 정보

        return "/cust/cust_faq_cancel";
    }

    // 자주 묻는 질문 - 안전거래
    @GetMapping("/cust_faq_safe")
    public String faq_safe(Model model, FaqPageRequestDTO pageRequestDTO) {
        String cate1 = "safe";  // cate1은 "safe_transaction"로 고정

        // 서비스에서 cate2별로 그룹화된 FAQ 항목 가져오기
        FaqPageResponseDTO pageResponseDTO = custService.faqFindAllByCate1(pageRequestDTO, cate1);

        // 그룹화된 데이터를 모델에 전달
        model.addAttribute("faq", pageResponseDTO.getDtoList());  // 전체 FAQ 리스트
        model.addAttribute("groupedByCate2", pageResponseDTO.getGroupedByCate2());  // cate2별로 그룹화된 FAQ 데이터
        model.addAttribute("page", pageResponseDTO);  // 페이징 처리 정보

        return "/cust/cust_faq_safe";
    }

    // 자주 묻는 질문 - 여행/숙박/항공
    @GetMapping("/cust_faq_travel")
    public String faq_travel(Model model, FaqPageRequestDTO pageRequestDTO) {
        String cate1 = "travel";  // cate1은 "travel_accommodation_flight"로 고정

        // 서비스에서 cate2별로 그룹화된 FAQ 항목 가져오기
        FaqPageResponseDTO pageResponseDTO = custService.faqFindAllByCate1(pageRequestDTO, cate1);

        // 그룹화된 데이터를 모델에 전달
        model.addAttribute("faq", pageResponseDTO.getDtoList());  // 전체 FAQ 리스트
        model.addAttribute("groupedByCate2", pageResponseDTO.getGroupedByCate2());  // cate2별로 그룹화된 FAQ 데이터
        model.addAttribute("page", pageResponseDTO);  // 페이징 처리 정보

        return "/cust/cust_faq_travel";
    }

    // 자주 묻는 질문 - 회원
    @GetMapping("/cust_faq_member")
    public String faq_user(Model model, FaqPageRequestDTO pageRequestDTO) {
        String cate1 = "member";  // cate1은 "user"로 고정

        // 서비스에서 cate2별로 그룹화된 FAQ 항목 가져오기
        FaqPageResponseDTO pageResponseDTO = custService.faqFindAllByCate1(pageRequestDTO, cate1);

        // 그룹화된 데이터를 모델에 전달
        model.addAttribute("faq", pageResponseDTO.getDtoList());  // 전체 FAQ 리스트
        model.addAttribute("groupedByCate2", pageResponseDTO.getGroupedByCate2());  // cate2별로 그룹화된 FAQ 데이터
        model.addAttribute("page", pageResponseDTO);  // 페이징 처리 정보

        return "/cust/cust_faq_member";
    }

    //자주묻는질문 - 글보기
    @GetMapping("/cust_faq_view")
    public String faq_view(@RequestParam int faqNo, Model model){
        FaqDTO faqDTO = custService.findFaqById(faqNo);

        model.addAttribute("faq", faqDTO);
        model.addAttribute("cate1", faqDTO.getCate1());
        return "/cust/cust_faq_view";
    }
    /* ***************************자주묻는질문 끝*************************** */


    //문의하기 - 리스트
    @GetMapping("/cust_question")
    public String question_user(Model model, QnaPageRequestDTO pageRequestDTO, @RequestParam(name = "cate1", required = true) String cate1) {
        QnaPageResponseDTO pageResponseDTO = custService.qnaFindAllByCate1(pageRequestDTO, cate1);

        List<QnaDTO> qnaList = pageResponseDTO.getDtoList();
        for(QnaDTO qnaDTO : qnaList){
            qnaDTO.setCate1(qnaDTO.getCate1Name());
        }

        model.addAttribute("article", pageResponseDTO.getDtoList());
        model.addAttribute("page", pageResponseDTO);
        model.addAttribute("cate1", cate1);

        return "/cust/cust_question";
    }
    //문의하기 - 글보기
    @GetMapping("/cust_question_view")
    public String question_view(@RequestParam int qnaNo, Model model){
        QnaDTO qnaDTO = custService.findQnaById(qnaNo);

        model.addAttribute("qna", qnaDTO);
        model.addAttribute("cate1", qnaDTO.getCate1());

        return "/cust/cust_question_view";
    }
    //문의하기 - 글쓰기
    @GetMapping("/cust_question_write")
    public String question_write_view(){

        return "/cust/cust_question_write";
    }

    @PostMapping("/cust_question_write")
    public String question_write(QnaDTO qnaDTO){
        int qnaNo = custService.qnaWrite(qnaDTO);

        return "redirect:/cust/cust_question_view?qnaNo=" + qnaNo;
    }
    /* ***************************문의하기 끝*************************** */



}
