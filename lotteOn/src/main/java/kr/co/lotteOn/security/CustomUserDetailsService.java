package kr.co.lotteOn.security;

import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 유저의 상태가 '중지'일 경우 로그인 차단
        if ("중지".equals(member.getStatus())) {
            throw new DisabledException("This account is deactivated.");
        }

        // 정상적으로 로그인 처리
        return new org.springframework.security.core.userdetails.User(
                member.getId(),
                member.getPassword(),
                AuthorityUtils.createAuthorityList("ROLE_MEMBER") // 권한 설정
        );
    }
}