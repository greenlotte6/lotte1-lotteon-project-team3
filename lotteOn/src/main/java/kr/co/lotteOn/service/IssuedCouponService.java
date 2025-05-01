package kr.co.lotteOn.service;

import kr.co.lotteOn.repository.IssuedCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class IssuedCouponService {

    private final IssuedCouponRepository issuedCouponRepository;
}
