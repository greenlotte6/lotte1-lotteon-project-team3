package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.coupon.CouponDTO;
import kr.co.lotteOn.dto.MemberDTO;
import kr.co.lotteOn.dto.coupon.CouponPageRequestDTO;
import kr.co.lotteOn.dto.coupon.CouponPageResponseDTO;
import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponPageRequestDTO;
import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponPageResponseDTO;
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
    @GetMapping("/member/search")
    public String search(@RequestParam("type") String type,
                       @RequestParam("keyword") String keyword,
                       Model model) {

        List<MemberDTO> memberList;

        if (type != null && keyword != null) {
            memberList = adminMemberService.searchMembers(type, keyword);
        } else {
            memberList = adminMemberService.findAll();
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
    public String couponList(Model model, CouponPageRequestDTO pageRequestDTO){

        CouponPageResponseDTO couponPageResponseDTO = adminMemberService.couponFindAll(pageRequestDTO);

        model.addAttribute("page", couponPageResponseDTO);
        model.addAttribute("coupons", couponPageResponseDTO.getDtoList());

        return "/admin/coupon/list";
    }

    //쿠폰관리 - 목록(검색)
    @GetMapping("/coupon/listSearch")
    public String couponListSearch(CouponPageRequestDTO pageRequestDTO, Model model){
        CouponPageResponseDTO pageResponseDTO = adminMemberService.couponSearchAll(pageRequestDTO);

        model.addAttribute("page", pageResponseDTO);
        model.addAttribute("coupons", pageResponseDTO.getDtoList());

        return "/admin/coupon/listSearch";
    }

    //쿠폰관리 - 등록
    @PostMapping("/coupon/list")
    public String registerCoupon(CouponDTO couponDTO){
        int issueNo = adminMemberService.registerCoupon(couponDTO);

        System.out.println("발급된 쿠폰 번호: " + issueNo);

        return "redirect:/admin/coupon/list";
    }

    //쿠폰관리 - 등록(검색)
    @PostMapping("/coupon/listSearch")
    public String registerCouponSearch(CouponDTO couponDTO){
        int issueNo = adminMemberService.registerCoupon(couponDTO);

        System.out.println("발급된 쿠폰 번호: " + issueNo);

        return "redirect:/admin/coupon/list";
    }

    //쿠폰 종료
    @PostMapping("/coupon/endCoupon")
    public String endCoupon(@RequestParam("couponCode") String couponCode) {
        adminMemberService.endCoupon(couponCode);

        return "redirect:/admin/coupon/list";  // 수정 후 다시 쿠폰 리스트로 리다이렉트
    }


    //쿠폰관리 - 쿠폰발급현황 리스트
    @GetMapping("/coupon/issued")
    public String couponIssued(Model model, IssuedCouponPageRequestDTO pageRequestDTO){
        IssuedCouponPageResponseDTO pageResponseDTO = adminMemberService.issuedCouponFindAll(pageRequestDTO);

        model.addAttribute("page", pageResponseDTO);
        model.addAttribute("coupons", pageResponseDTO.getDtoList());

        return "/admin/coupon/issued";
    }
    //쿠폰관리 - 쿠폰발급현황 리스트(검색)
    @GetMapping("/coupon/issuedSearch")
    public String couponIssuedSearch(Model model, IssuedCouponPageRequestDTO pageRequestDTO){
        IssuedCouponPageResponseDTO pageResponseDTO = adminMemberService.issuedCouponSearchAll(pageRequestDTO);

        model.addAttribute("page", pageResponseDTO);
        model.addAttribute("coupons", pageResponseDTO.getDtoList());

        return "/admin/coupon/issuedSearch";
    }

    //쿠폰관리 - 쿠폰발급현황 리스트(종료)
    @PostMapping("/coupon/endIssued")
    public String endIssued(@RequestParam("issuedNo") int issuedNo){
        adminMemberService.endIssuedCoupon(issuedNo);

        return "redirect:/admin/coupon/issued";
    }

}
