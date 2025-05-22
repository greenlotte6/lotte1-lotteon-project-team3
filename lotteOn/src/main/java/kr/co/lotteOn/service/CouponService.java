package kr.co.lotteOn.service;

import kr.co.lotteOn.entity.Coupon;
import kr.co.lotteOn.entity.IssuedCoupon;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.repository.CouponRepository;
import kr.co.lotteOn.repository.IssuedCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final IssuedCouponRepository issuedCouponRepository;

    @Transactional
    public IssuedCoupon issueRandomCoupon(Member member) {
        List<Coupon> coupons = couponRepository.findByStatus("발급중");
        if (coupons.isEmpty()) {
            throw new IllegalStateException("발급 가능한 쿠폰이 없습니다.");
        }

        Coupon selected = coupons.get(new Random().nextInt(coupons.size()));

        // 발급 수량 초과 체크
        if (selected.getUseCount() >= selected.getIssueCount()) {
            throw new IllegalStateException("쿠폰 발급 수량을 초과했습니다.");
        }

        selected.increaseUseCount(); // useCount 증가 및 상태 변경 처리

        IssuedCoupon issued = IssuedCoupon.builder()
                .coupon(selected)
                .member(member)
                .expiredDate(LocalDateTime.parse(selected.getEndDate() + "T23:59:59"))
                .status("미사용")
                .build();

        return issuedCouponRepository.save(issued);
    }
}