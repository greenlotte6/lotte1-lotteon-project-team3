package kr.co.lotteOn.controller.test;

import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.repository.CouponRepository;
import kr.co.lotteOn.repository.MemberRepository;
import kr.co.lotteOn.security.MyUserDetails;
import kr.co.lotteOn.service.IssuedCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class IssuedCouponTest {

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final IssuedCouponService issuedCouponService;

    @GetMapping("/test/addCoupon")
    public String testIssueCoupon(@AuthenticationPrincipal MyUserDetails userDetails) {
        String memberId = userDetails.getUsername();
        Member member = memberRepository.findById(memberId).orElseThrow();

        issuedCouponService.issueCoupon(member, "GENERIC-F49F98");

        return "쿠폰 발급 완료";
    }
}
