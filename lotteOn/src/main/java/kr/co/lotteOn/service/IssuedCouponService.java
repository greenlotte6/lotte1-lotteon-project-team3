package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponDTO;
import kr.co.lotteOn.entity.Coupon;
import kr.co.lotteOn.entity.IssuedCoupon;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.repository.CouponRepository;
import kr.co.lotteOn.repository.IssuedCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class IssuedCouponService {

    private final IssuedCouponRepository issuedCouponRepository;
    private final CouponRepository couponRepository;

    //payment페이지에 회원별 쿠폰 select하는 메서드
    public List<IssuedCouponDTO> getAvailableCouponsForMember(String memberId) {
        return issuedCouponRepository.findAvailableCouponsByMemberId(memberId);
    }


    public void issueCoupon(Member member, String couponCode) {
        // 쿠폰 조회 (이미 생성된 쿠폰 코드로)
        Coupon coupon = couponRepository.findByCouponCode(couponCode)
                .orElseThrow(() -> new RuntimeException("쿠폰이 존재하지 않습니다"));

        // 발급 내역 생성
        IssuedCoupon issuedCoupon = IssuedCoupon.builder()
                .member(member) // 회원
                .coupon(coupon) // 쿠폰
                .status("미사용")
                .issuedDate(LocalDateTime.now()) // 발급일
                .expiredDate(LocalDateTime.now().plusDays(30))
                .build();

        // 발급 내역 저장
        issuedCouponRepository.save(issuedCoupon);
    }
}
