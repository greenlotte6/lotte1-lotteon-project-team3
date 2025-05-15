package kr.co.lotteOn.controller;


import kr.co.lotteOn.dto.MemberDTO;
import kr.co.lotteOn.dto.refund.RefundDTO;
import kr.co.lotteOn.dto.refund.RefundPageRequestDTO;
import kr.co.lotteOn.dto.refund.RefundPageResponseDTO;
import kr.co.lotteOn.dto.review.ReviewDTO;
import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponDTO;
import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponPageRequestDTO;
import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponPageResponseDTO;
import kr.co.lotteOn.dto.order.OrderDTO;
import kr.co.lotteOn.dto.order.OrderPageRequestDTO;
import kr.co.lotteOn.dto.order.OrderPageResponseDTO;
import kr.co.lotteOn.dto.point.PointDTO;
import kr.co.lotteOn.dto.point.PointPageRequestDTO;
import kr.co.lotteOn.dto.point.PointPageResponseDTO;
import kr.co.lotteOn.dto.qna.QnaDTO;
import kr.co.lotteOn.dto.qna.QnaPageRequestDTO;
import kr.co.lotteOn.dto.qna.QnaPageResponseDTO;
import kr.co.lotteOn.dto.review.ReviewPageRequestDTO;
import kr.co.lotteOn.dto.review.ReviewPageResponseDTO;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
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

    @ModelAttribute
    public void getTotal(@AuthenticationPrincipal MyUserDetails userDetails, Model model){
        // 1. 로그인한 회원의 ID 가져오기
        String memberId = userDetails.getUsername();

        // 2. MemberDTO 생성
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberId);

        // Qna 게시글 총 개수 조회
        int totalQnaCount = myPageService.countQnaByWriter(memberId);
        int totalPointCount = myPageService.getLatestTotalPoint(memberId);
        int totalCoupon = myPageService.countIssuedByMemberId(memberId);
        int totalOrder = myPageService.countOrderByMemberId(memberId);

        //모델에 추가
        model.addAttribute("totalQnaCount", totalQnaCount);
        model.addAttribute("totalPointCount", totalPointCount);
        model.addAttribute("totalCoupon", totalCoupon);
        model.addAttribute("totalOrder", totalOrder);

    }

    //문의글작성하기(홈)
    @PostMapping("/my_seller_qnaH")
    public String sellerQnaWriteH(QnaDTO qnaDTO){
        int no = myPageService.qnaWrite(qnaDTO);

        return "redirect:/myPage/my_home";
    }
    //문의글작성하기(주문목록)
    @PostMapping("/my_seller_qna")
    public String sellerQnaWrite(QnaDTO qnaDTO){
        int no = myPageService.qnaWrite(qnaDTO);

        return "redirect:/myPage/my_order";
    }

    //구매확정하기(홈)
    @PostMapping("/my_confirmH")
    public String confirmPurchaseH(@RequestParam("orderCode") String orderCode) {
        System.out.println(orderCode);

        myPageService.confirmPurchase(orderCode);

        return "redirect:/myPage/my_home";
    }
    //구매확정하기(주문목록)
    @PostMapping("/my_confirm")
    public String confirmPurchase(@RequestParam("orderCode") String orderCode) {
        System.out.println(orderCode);

        myPageService.confirmPurchase(orderCode);

        return "redirect:/myPage/my_order";
    }

    //리뷰쓰기(홈)
    @PostMapping("/my_review_writeH")
    public String writeReviewH(ReviewDTO reviewDTO) {
        int no = myPageService.writeReview(reviewDTO);

        return "redirect:/myPage/my_home";
    }

    //리뷰쓰기(주문내역)
    @PostMapping("/my_review_write")
    public String writeReview(ReviewDTO reviewDTO) {
        int no = myPageService.writeReview(reviewDTO);

        return "redirect:/myPage/my_order";
    }

    //반품신청(홈)
    @PostMapping("/my_refundH")
    public String writeRefundH(RefundDTO refundDTO) {
        refundDTO.setChannel("반품");
        int no = myPageService.writeRefund(refundDTO);

        return "redirect:/myPage/my_home";
    }

    //반품신청(주문내역)
    @PostMapping("/my_refund")
    public String writeRefund(RefundDTO refundDTO) {
        refundDTO.setChannel("반품");
        int no = myPageService.writeRefund(refundDTO);

        return "redirect:/myPage/my_order";
    }

    //교환신청(홈)
    @PostMapping("/my_exchangeH")
    public String writeExchangeH(RefundDTO refundDTO) {
        refundDTO.setChannel("교환");
        int no = myPageService.writeRefund(refundDTO);

        return "redirect:/myPage/my_home";
    }

    //교환신청(주문내역)
    @PostMapping("/my_exchange")
    public String writeExchange(RefundDTO refundDTO) {
        refundDTO.setChannel("교환");
        int no = myPageService.writeRefund(refundDTO);

        return "redirect:/myPage/my_order";
    }


    //마이페이지 - 메인
    @GetMapping("/my_home")
    public String myHome(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {

        // 1. 로그인한 회원의 ID 가져오기
        String memberId = userDetails.getUsername();

        // 2. MemberDTO 생성
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberId);

        // 3. 최근 문의 3개 조회
        List<QnaDTO> recentQnaTop3 = myPageService.findByMemberIdByLimit3(memberDTO);
        List<PointDTO> recentPointTop3 = myPageService.findPointByMemberIdByLimit3(memberDTO);
        List<OrderDTO> recentOrderTop3 = myPageService.findOrderByMemberIdByLimit3(memberDTO);
        List<ReviewDTO> recentReviewTop3 = myPageService.findReviewByMemberIdByLimit3(memberDTO);
        List<RefundDTO> recentRefundTop3 = myPageService.getTop3RefundsByMember(memberId);
        for(OrderDTO orderDTO : recentOrderTop3){
            orderDTO.setPayment(orderDTO.getPaymentName());
        }
        for(RefundDTO refundDTO : recentRefundTop3) {
            refundDTO.setRefundType(refundDTO.getReturnType());
        }
        for(ReviewDTO reviewDTO : recentReviewTop3) {
            reviewDTO.setRating(reviewDTO.getRatingStar());
        }
        // 4. 모델에 추가
        model.addAttribute("recentQna", recentQnaTop3);
        model.addAttribute("recentPoint", recentPointTop3);
        model.addAttribute("recentOrder", recentOrderTop3);
        model.addAttribute("recentReview", recentReviewTop3);
        model.addAttribute("recentRefund", recentRefundTop3);

        return "/myPage/my_home";
    }

    //마이페이지 - 전체주문내역
    @GetMapping("/my_order")
    public String myOrder(@AuthenticationPrincipal MyUserDetails userDetails, Model model,
                          OrderPageRequestDTO pageRequestDTO) {
        // 1. 로그인한 회원의 ID 가져오기
        Member currentMember = userDetails.getMember();
        String loginId = currentMember.getId();

        // 2. MemberDTO 생성
        pageRequestDTO.setMemberId(loginId);

        // 3. Service 호출
        OrderPageResponseDTO pageResponseDTO = myPageService.getOrderByMemberId(pageRequestDTO);
        model.addAttribute("page", pageResponseDTO);
        model.addAttribute("order", pageResponseDTO.getDtoList());

        return "/myPage/my_order";
    }

    //마이페이지 - 포인트내역
    @GetMapping("/my_point")
    public String myPoint(@AuthenticationPrincipal MyUserDetails userDetails, Model model,
                          @ModelAttribute Member member, PointPageRequestDTO pageRequestDTO) {

        Member currentMember = userDetails.getMember();
        String loginId = currentMember.getId();

        pageRequestDTO.setMemberId(loginId);

        PointPageResponseDTO pageResponseDTO = myPageService.getPointByMemberId(pageRequestDTO);
        model.addAttribute("page", pageResponseDTO);
        model.addAttribute("point", pageResponseDTO.getDtoList());

        return "/myPage/my_point";
    }

    //마이페이지 - 쿠폰
    @GetMapping("/my_coupon")
    public String myCoupon(@AuthenticationPrincipal MyUserDetails userDetails, Model model,
                           IssuedCouponPageRequestDTO pageRequestDTO, @ModelAttribute Member member) {

        Member currentMember = userDetails.getMember();

        String loginId = currentMember.getId();

        pageRequestDTO.setMemberId(loginId);

        IssuedCouponPageResponseDTO pageResponseDTO = myPageService.getCouponByWriter(pageRequestDTO);
        model.addAttribute("page", pageResponseDTO);
        model.addAttribute("coupons", pageResponseDTO.getDtoList());

        return "/myPage/my_coupon";
    }

    //마이페이지 - 쿠폰 디테일
    @GetMapping("/my_view_coupon")
    public String myCouponView(Integer issuedNo, Model model) {
        IssuedCouponDTO issuedCouponDTO = myPageService.findCouponById(issuedNo);
        model.addAttribute("coupon", issuedCouponDTO);

        return "/myPage/my_view_coupon";
    }

    //마이페이지 - 나의리뷰
    @GetMapping("/my_review")
    public String myReview(@AuthenticationPrincipal MyUserDetails userDetails, Model model,
                           @ModelAttribute Member member, ReviewPageRequestDTO pageRequestDTO) {
        Member currentMember = userDetails.getMember();
        String loginId = currentMember.getId();
        pageRequestDTO.setWriter(loginId);

        ReviewPageResponseDTO pageResponseDTO = myPageService.getReviewByWriter(pageRequestDTO);

        model.addAttribute("page", pageResponseDTO);
        model.addAttribute("review", pageResponseDTO.getDtoList());

        return "/myPage/my_review";
    }

    //마이페이지 - 문의하기 글 보기
    @GetMapping("/my_view")
    public String myQnaView(Integer qnaNo, Model model) {
        QnaDTO qnaDTO = myPageService.findById(qnaNo);

        model.addAttribute("qna", qnaDTO);

        return "/myPage/my_view";
    }

    //마이페이지 - 문의하기
    @GetMapping("/my_qna")
    public String myQnaList(@AuthenticationPrincipal MyUserDetails userDetails, Model model,
                            QnaPageRequestDTO pageRequestDTO, @ModelAttribute Member member) {
        
        log.info("pageRequestDTO:{}", pageRequestDTO);

        //현재 접속한 사용자 정보
        Member currentMember = userDetails.getMember();

        log.info("currentMember:{}", currentMember);

        String loginId = currentMember.getId();

        log.info("현재 로그인한 ID: {}", loginId);

        // 로그인한 사용자 ID 설정
        pageRequestDTO.setWriter(loginId);

        QnaPageResponseDTO pageResponseDTO = myPageService.getQnaByWriter(pageRequestDTO);

        log.info("pageResponseDTO:{}", pageResponseDTO);

        model.addAttribute("page", pageResponseDTO);
        model.addAttribute("qna", pageResponseDTO.getDtoList());



        return "/myPage/my_qna"; // 실제 뷰 경로
    }

    //마이페이지 - 반품/교환신청 내역
    @GetMapping("/my_refundList")
    public String myRefundList(@AuthenticationPrincipal MyUserDetails userDetails, Model model,
                               @ModelAttribute Member member, RefundPageRequestDTO pageRequestDTO) {
        Member currentMember = userDetails.getMember();
        String loginId = currentMember.getId();
        pageRequestDTO.setMemberId(loginId);

        RefundPageResponseDTO pageResponseDTO = myPageService.getRefundByMemberId(pageRequestDTO);
        for(RefundDTO refundDTO : pageResponseDTO.getDtoList()) {
            refundDTO.setRefundType(refundDTO.getReturnType());
        }
        model.addAttribute("page", pageResponseDTO);
        model.addAttribute("refund", pageResponseDTO.getDtoList());

        return "/myPage/my_refundList";
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
