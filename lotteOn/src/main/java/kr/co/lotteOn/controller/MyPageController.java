package kr.co.lotteOn.controller;


import kr.co.lotteOn.dto.qna.QnaPageRequestDTO;
import kr.co.lotteOn.dto.qna.QnaPageResponseDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.repository.MemberRepository;
import kr.co.lotteOn.security.MyUserDetails;
import kr.co.lotteOn.service.MemberService;
import kr.co.lotteOn.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/myPage")
@Controller
public class MyPageController {

    private final MemberService memberService;
    private final MyPageService myPageService;
    private final MemberRepository memberRepository;

    @Lazy
    private final PasswordEncoder passwordEncoder;

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

    //마이페이지 - 나의리뷰
    @GetMapping("/my_view")
    public String myQnaView() {
        return "/myPage/my_view";
    }

    //마이페이지 - 문의하기
    @GetMapping("/my_qna")
    public String myQnaList(@AuthenticationPrincipal MyUserDetails userDetails, Model model,
                            QnaPageRequestDTO pageRequestDTO, @ModelAttribute Member member) {

//        QnaPageResponseDTO qnaPageResponseDTO = myPageService.getAll(pageRequestDTO);
//        model.addAttribute("page", qnaPageResponseDTO);
//        model.addAttribute("all", qnaPageResponseDTO.getDtoList());


        log.debug("pageRequestDTO:{}", pageRequestDTO);

        //현재 접속한 사용자 정보
        Member currentMember = userDetails.getMember();

        log.debug("currentMember:{}", currentMember);

        String loginId = currentMember.getId();

        log.debug("현재 로그인한 ID: {}", loginId);

        // 로그인한 사용자 ID 설정
        pageRequestDTO.setWriter(loginId);

        QnaPageResponseDTO pageResponseDTO = myPageService.getQnaByWriter(pageRequestDTO);

        log.debug("pageResponseDTO:{}", pageResponseDTO);

        model.addAttribute("page", pageResponseDTO);
        model.addAttribute("qna", pageResponseDTO.getDtoList());



        return "/myPage/my_qna"; // 실제 뷰 경로
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

        // 비밀번호가 입력되었을 때만 암호화 후 저장
        if (member.getPassword() != null && !member.getPassword().isEmpty()) {
            String encodedPw = passwordEncoder.encode(member.getPassword());
            currentMember.setPassword(encodedPw);
        }

        currentMember.setAddr1(member.getAddr1());
        currentMember.setAddr2(member.getAddr2());
        currentMember.setZip(member.getZip());

        memberRepository.save(currentMember);

        return "redirect:/myPage/my_info";

    }

    @PostMapping("/my_info/leave")
    public String leaveMember(@AuthenticationPrincipal MyUserDetails userDetails) {
        Member member = userDetails.getMember();

        member.setStatus("탈퇴"); // 또는 "DELETED", "WITHDRAWN" 등으로 관리
        member.setLeaveDate(LocalDateTime.now());

        // 비밀번호를 무작위 문자열로 변경 (로그인 불가하게)
        String randomPassword = UUID.randomUUID().toString(); // 난수 생성
        String encodedPassword = passwordEncoder.encode(randomPassword);

        log.info("randomPassword: " + randomPassword);
        log.info("encodedPassword: " + encodedPassword);
        log.info("member.getPassword(): " + member.getPassword());

        member.setPassword(encodedPassword);

        memberRepository.save(member);

        // 로그아웃 처리 등 추가 가능
        return "redirect:/member/logout";
    }


}
