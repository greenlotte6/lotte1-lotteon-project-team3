package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.coupon.CouponDTO;
import kr.co.lotteOn.dto.MemberDTO;
import kr.co.lotteOn.dto.coupon.CouponPageRequestDTO;
import kr.co.lotteOn.dto.coupon.CouponPageResponseDTO;
import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponPageRequestDTO;
import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponPageResponseDTO;
import kr.co.lotteOn.dto.point.PointPageRequestDTO;
import kr.co.lotteOn.dto.point.PointPageResponseDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.repository.MemberRepository;
import kr.co.lotteOn.service.AdminMemberService;
import kr.co.lotteOn.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
@Slf4j
public class AdminMemberController {

    private final AdminMemberService adminMemberService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    /*------------ 관리자 - 고객관리 ------------*/

    //고객관리 - 목록
    @GetMapping("/member/list")
    public String memberList(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             Model model) {
        Page<MemberDTO> memberPage = adminMemberService.findAll(page, size);

        int pageGroupSize = 10;
        int startPage = (page / pageGroupSize) * pageGroupSize;
        int endPage = Math.min(startPage + pageGroupSize - 1, memberPage.getTotalPages() - 1);

        model.addAttribute("memberList", memberPage.getContent());
        model.addAttribute("memberPage", memberPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", memberPage.getTotalPages());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

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
    public String search(@RequestParam(value = "type", required = false) String type,
                         @RequestParam(value = "keyword", required = false) String keyword,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "size", defaultValue = "10") int size,
                         Model model) {

        Page<MemberDTO> memberPage;

        if (type != null && !type.isEmpty() && keyword != null && !keyword.isEmpty()) {
            memberPage = adminMemberService.searchMembers(type, keyword, page, size);
        } else {
            memberPage = adminMemberService.findAll(page, size);
        }

        int pageGroupSize = 10;
        int startPage = (page / pageGroupSize) * pageGroupSize;
        int endPage = Math.min(startPage + pageGroupSize - 1, memberPage.getTotalPages() - 1);

        model.addAttribute("memberPage", memberPage);
        model.addAttribute("memberList", memberPage.getContent());
        model.addAttribute("type", type);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", memberPage.getTotalPages());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "/admin/member/list";
    }

    //고객관리 - 포인트현황
    @GetMapping("/member/point")
    public String memberPoint(Model model, PointPageRequestDTO pageRequestDTO) {
        PointPageResponseDTO pointPageResponseDTO = adminMemberService.pointList(pageRequestDTO);

        model.addAttribute("page", pointPageResponseDTO);
        model.addAttribute("points", pointPageResponseDTO.getDtoList());

        return "/admin/member/point";
    }
    //고객관리 - 포인트현황
    @GetMapping("/member/pointSearch")
    public String memberPointSearch(Model model, PointPageRequestDTO pageRequestDTO) {
        PointPageResponseDTO pointPageResponseDTO = adminMemberService.pointListSearch(pageRequestDTO);

        model.addAttribute("page", pointPageResponseDTO);
        model.addAttribute("point", pointPageResponseDTO.getDtoList());

        return "/admin/member/pointSearch";
    }

    @PostMapping("/member/deactivate/{id}")
    public String deactivateMember(@PathVariable String id) {
        adminMemberService.updateMemberStatus(id, "중지");
        return "redirect:/admin/member/list";
    }

    @PostMapping("/member/reactivate/{id}")
    public String reactivateMember(@PathVariable String id) {
        adminMemberService.updateMemberStatus(id, "정상");
        return "redirect:/admin/member/list";
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

    @GetMapping("/coupon/listSearch")
    public String couponListSearch(CouponPageRequestDTO pageRequestDTO, Model model) {
        try {
            CouponPageResponseDTO pageResponseDTO = adminMemberService.couponSearchAll(pageRequestDTO);
            List<CouponDTO> couponList = pageResponseDTO.getDtoList();
            model.addAttribute("page", pageResponseDTO);
            model.addAttribute("coupons", couponList != null ? couponList : new ArrayList<>());
            return "/admin/coupon/listSearch";
        } catch (Exception e) {
            e.printStackTrace(); // 로그로 예외 확인
            model.addAttribute("error", "쿠폰 목록을 불러오는 도중 오류가 발생했습니다.");
            return "/admin/coupon/list"; // 별도의 에러 페이지 또는 기본 페이지로 리턴
        }
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
