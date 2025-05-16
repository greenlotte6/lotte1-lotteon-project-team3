package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.TermsDTO;
import kr.co.lotteOn.dto.notice.NoticeDTO;
import kr.co.lotteOn.dto.qna.QnaDTO;
import kr.co.lotteOn.entity.Banner;
import kr.co.lotteOn.entity.Config;
import kr.co.lotteOn.entity.Order;
import kr.co.lotteOn.repository.BannerRepository;
import kr.co.lotteOn.repository.ConfigRepository;
import kr.co.lotteOn.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminLotteController {

    private final TermsService termsService;
    private final AdminCSService adminCSService;
    private final AdminLotteService adminLotteService;
    private final ConfigRepository configRepository;
    private final BannerRepository bannerRepository;
    private final OrderService orderService;
    private final VisitCounterService visitCounterService;

    //관리자 - 메인
    @GetMapping("/admin")
    public String admin(Model model){

        Map<String, Long> orderStats = orderService.getOrderStatusSummary();
        Map<String, Long> dailyStats = orderService.getDailyStats();
        model.addAttribute("orderStats", orderStats);
        model.addAttribute("dailyStats", dailyStats);

        List<NoticeDTO> notices = adminLotteService.findAllNoticeByLimit5();
        List<QnaDTO> qnas = adminLotteService.findAllQnaByLimit5();

        model.addAttribute("notices", notices);
        model.addAttribute("qnas", qnas);


        /* --------------- 방문자 수 카운트 로직 --------------- */
        String uri = "/";

        long todayCount = visitCounterService.getTodayVisitCount(uri);
        long yesterdayCount = visitCounterService.getYesterdayVisitCount(uri);

        model.addAttribute("todayVisit", todayCount);
        model.addAttribute("yesterdayVisit", yesterdayCount);

        return "/admin/admin";
        /* ------------------------------------------------- */
    }




    /*------------ 관리자 - 상품관리 ------------*/



    /*------------ 관리자 - 상점관리 ------------*/

    //환경설정 - 기본설정
    @GetMapping("/config/basic")
    public String basic(Model model) {
        Config config = configRepository.findById(1).orElse(new Config());
        model.addAttribute("config", config);
        return "/admin/config/basic";
    }

    //환경설정 - 배너관리
    @GetMapping("/config/banner")
    public String banner(Model model) {
        List<Banner> banners = bannerRepository.findAll();
        model.addAttribute("banners", banners);
        return "/admin/config/banner";
    }
    @GetMapping("/config/bannerLogin")
    public String bannerLogin(Model model) {
        List<Banner> banners = bannerRepository.findAll();
        model.addAttribute("banners", banners);
        return "/admin/config/bannerLogin";
    }
    @GetMapping("/config/bannerMainsl")
    public String bannerMainsl(Model model){
        List<Banner> banners = bannerRepository.findAll();
        model.addAttribute("banners", banners);
        return "/admin/config/bannerMainsl";
    }
    @GetMapping("/config/bannerMypage")
    public String bannerMypage(Model model){
        List<Banner> banners = bannerRepository.findAll();
        model.addAttribute("banners", banners);
        return "/admin/config/bannerMypage";
    }
    @GetMapping("/config/bannerProduct")
    public String bannerProduct(Model model){
        List<Banner> banners = bannerRepository.findAll();
        model.addAttribute("banners", banners);
        return "/admin/config/bannerProduct";
    }

    //환경설정 - 약관관리
    @GetMapping("/config/policy")
    public String policy(Model model){
        TermsDTO termsDTO = termsService.findTerms();

        model.addAttribute("termsDTO", termsDTO);

        return "/admin/config/policy";
    }

    @PostMapping("/config/policyModify")
    public String policyModify(TermsDTO termsDTO){
        termsService.modifyTerms(termsDTO);

        return "redirect:/admin/config/policy";

    }

    //환경설정 - 카테고리 관리
    @GetMapping("/config/category")
    public String category() {
        return "/admin/config/category";
    }

}
