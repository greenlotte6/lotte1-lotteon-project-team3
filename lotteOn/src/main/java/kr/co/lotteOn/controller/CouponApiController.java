package kr.co.lotteOn.controller;

import kr.co.lotteOn.entity.IssuedCoupon;
import kr.co.lotteOn.security.MyUserDetails;
import kr.co.lotteOn.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponApiController {

    private final CouponService couponService;

    @PostMapping("/issue-random")
    public ResponseEntity<?> issueRandomCoupon(@AuthenticationPrincipal MyUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "로그인이 필요합니다."));
        }

        try {
            IssuedCoupon issuedCoupon = couponService.issueRandomCoupon(userDetails.getMember());
            return ResponseEntity.ok(Map.of(
                    "couponName", issuedCoupon.getCoupon().getCouponName(),
                    "couponCode", issuedCoupon.getCoupon().getCouponCode()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}