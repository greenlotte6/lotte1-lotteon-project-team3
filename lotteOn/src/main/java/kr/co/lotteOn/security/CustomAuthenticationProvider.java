package kr.co.lotteOn.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final MyUserDetailsService userDetailsService;
    private final SellerUserDetailsService sellerUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final HttpServletRequest request;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        String userType = (String) request.getParameter("userType");
        log.info("CustomAuthenticationProvider - userType: {}", userType);

        UserDetails userDetails;

        // 사용자 유형에 따라 UserDetailsService 선택
        if ("seller".equals(userType)) {
            userDetails = sellerUserDetailsService.loadUserByUsername(username);
        } else {
            userDetails = userDetailsService.loadUserByUsername(username);
        }

        if (userDetails == null) {
            throw new BadCredentialsException("사용자를 찾을 수 없습니다.");
        }


        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
