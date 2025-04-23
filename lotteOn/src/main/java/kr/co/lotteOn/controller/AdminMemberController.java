package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.MemberDTO;
import kr.co.lotteOn.service.AdminMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
@Slf4j
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    /*------------ 관리자 - 고객관리 ------------*/

    //고객관리 - 목록
    @GetMapping("/member/list")
    public String memberList(Model model){
        List<MemberDTO> memberList = adminMemberService.findAll();
        model.addAttribute("memberList", memberList);
        return "/admin/member/list";
    }

    //회원수정
    @PostMapping("/member/modify")
    public String modify(MemberDTO memberDTO) {
        adminMemberService.modify(memberDTO);
        return "redirect:/admin/member/list";
    }

    //검색
    @GetMapping("/admin/member/list")
    public String list(@RequestParam(required = false) String type,
                       @RequestParam(required = false) String keyword,
                       Model model) {

        log.info("🔍 검색 요청: type={}, keyword={}", type, keyword);
        List<MemberDTO> memberList;

        if (type != null && keyword != null && !type.trim().isEmpty() && !keyword.trim().isEmpty()) {
            log.info("searchMembers() 실행");
            memberList = adminMemberService.searchMembers(type, keyword);
            log.info("searchMembers() 호출 결과: {}", memberList);
        } else {
            log.info("findAll() 실행");
            memberList = adminMemberService.findAll();
            log.info("findAll() 호출 결과: {}", memberList);
        }

        model.addAttribute("memberList", memberList);
        model.addAttribute("type", type);
        model.addAttribute("keyword", keyword);

        return "/admin/member/list";
    }




    //고객관리 - 포인트현황
    @GetMapping("/member/point")
    public String memberPoint(){
        return "/admin/member/point";
    }


    /*------------ 관리자 - 쿠폰관리 ------------*/

    //쿠폰관리 - 목록
    @GetMapping("/coupon/list")
    public String couponList(){
        return "/admin/coupon/list";
    }

    //쿠폰관리 - 쿠폰현황
    @GetMapping("/coupon/issued")
    public String couponIssued(){
        return "/admin/coupon/issued";
    }

}
