package kr.co.lotteOn.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Slf4j
@Setter
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException{

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String userType = request.getParameter("userType");

        //로그
        log.info("CustomAuthenticationFilter - userType: {}", userType);

        if(username == null) {
            username = "";
        }
        if(password == null) {
            password = "";
        }

        username = username.trim();

        // userType 로그 추가 (확인용)
        System.out.println("userType from request: " + userType);

        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(username, password);

        request.setAttribute("userType", userType);

        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
