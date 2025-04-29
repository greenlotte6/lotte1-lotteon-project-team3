package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.MemberDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.repository.MemberRepository;
import kr.co.lotteOn.security.MyUserDetails;
import kr.co.lotteOn.service.MemberService;
import kr.co.lotteOn.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/myPage")
@Controller
public class MyPageController {

    private final MemberService memberService;
    private final MyPageService myPageService;
    private final MemberRepository memberRepository;

    //마이페이지 - 메인
    @GetMapping("/my_home")
    public String myHome() {
        return "/myPage/my_home";
    }

    //마이페이지 - 전체주문내역
    @GetMapping("/my_order")
    public String myOrder() {
        return "/myPage/my_order";
    }
    //마이페이지 - 포인트내역
    @GetMapping("/my_point")
    public String myPoint() {
        return "/myPage/my_point";
    }

    //마이페이지 - 쿠폰
    @GetMapping("/my_coupon")
    public String myCoupon() {
        return "/myPage/my_coupon";
    }

    //마이페이지 - 나의리뷰
    @GetMapping("/my_review")
    public String myReview() {
        return "/myPage/my_review";
    }

    //마이페이지 - 문의하기
    @GetMapping("/my_qna")
    public String myQna() {
        return "/myPage/my_qna";
    }

    //마이페이지 - 나의설정
    @GetMapping("/my_info")
    public String myInfo(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {

        Member member = userDetails.getMember();

        model.addAttribute("member", member);

        return "/myPage/my_info";
    }

    @PostMapping("/my_info")
    public String modifyMyInfo(@AuthenticationPrincipal MyUserDetails userDetails, @ModelAttribute Member member) {
        //현재 접속한 사용자 정보
        Member currentMember = userDetails.getMember();

        currentMember.setEmail(member.getEmail());
        currentMember.setHp(member.getHp());
        currentMember.setPassword(member.getPassword());
        currentMember.setAddr1(member.getAddr1());
        currentMember.setAddr2(member.getAddr2());
        currentMember.setZip(member.getZip());

        memberRepository.save(currentMember);

        return "redirect:/myPage/my_info";

    }
}
