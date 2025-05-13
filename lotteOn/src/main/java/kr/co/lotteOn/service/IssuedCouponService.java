package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponDTO;
import kr.co.lotteOn.entity.Coupon;
import kr.co.lotteOn.entity.IssuedCoupon;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Order;
import kr.co.lotteOn.repository.CouponRepository;
import kr.co.lotteOn.repository.IssuedCouponRepository;
import kr.co.lotteOn.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class IssuedCouponService {

    private final IssuedCouponRepository issuedCouponRepository;
    private final CouponRepository couponRepository;
    private final OrderRepository orderRepository;

    //payment페이지에 회원별 쿠폰 select하는 메서드
    public List<IssuedCouponDTO> getAvailableCouponsForMember(String memberId) {
        return issuedCouponRepository.findAvailableCouponsByMemberId(memberId);
    }


    public void issueCoupon(Member member, String couponCode) {
        // 쿠폰 조회 (이미 생성된 쿠폰 코드로)
        Coupon coupon = couponRepository.findByCouponCode(couponCode)
                .orElseThrow(() -> new RuntimeException("쿠폰이 존재하지 않습니다"));

        // 발급 가능 여부 확인 및 useCount 증가
        if (coupon.getUseCount() >= coupon.getIssueCount()) {
            throw new IllegalStateException("쿠폰 발급 수량을 초과했습니다.");
        }

        coupon.setUseCount(coupon.getUseCount() + 1);

        // 발급 수량이 다 찼으면 상태 변경
        if (coupon.getUseCount() == coupon.getIssueCount()) {
            coupon.setStatus("발급 종료");
        }

        couponRepository.save(coupon); // 변경사항 저장

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

    @Transactional
    public void markCouponAsUsed(int issuedNo, String orderCode) {
        IssuedCoupon issuedCoupon = issuedCouponRepository.findByIssuedNo(issuedNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 쿠폰이 없습니다."));

        issuedCoupon.setUsed(true);
        issuedCoupon.setUseDate(LocalDateTime.now());
        issuedCoupon.setStatus("사용 불가");

        Order order = orderRepository.findByOrderCode(orderCode);
        issuedCoupon.setOrder(order);

        Coupon coupon = issuedCoupon.getCoupon();
        coupon.setUseCount(coupon.getUseCount() + 1);

    }
}
