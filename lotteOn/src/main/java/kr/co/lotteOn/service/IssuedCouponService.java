package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponDTO;
import kr.co.lotteOn.repository.IssuedCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class IssuedCouponService {

    private final IssuedCouponRepository issuedCouponRepository;

    //payment페이지에 회원별 쿠폰 select하는 메서드
    public List<IssuedCouponDTO> getAvailableCouponsForMember(String memberId) {
        return issuedCouponRepository.findAvailableCouponsByMemberId(memberId);
    }
}
