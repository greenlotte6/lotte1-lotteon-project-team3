package kr.co.lotteOn.security;

import kr.co.lotteOn.entity.Seller;
import kr.co.lotteOn.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service("sellerUserDetailsService")
public class SellerUserDetailsService implements UserDetailsService {

    private final SellerRepository sellerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("판매자 로그인 시도: {}", username);
        Optional<Seller> optSeller= sellerRepository.findById(username);
        if(optSeller.isPresent()) {
            log.info("판매자 정보: {}",optSeller.get());
            return SellerUserDetails.builder()
                    .seller(optSeller.get())
                    .build();
        }else{
            log.info("판매자 정보 없음: {}",username);
        }
        throw new UsernameNotFoundException("판매자 계정을 찾을 수 없습니다.");
    }
}
