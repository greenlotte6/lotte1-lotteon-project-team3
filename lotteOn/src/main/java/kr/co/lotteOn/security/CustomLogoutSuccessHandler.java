package kr.co.lotteOn.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Slf4j
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication)throws IOException {
        String from = request.getParameter("from");

        if(from == null || from.isEmpty()) {
            from = "default";
        }
        log.info("logout triggered. 'from' : {}", from);

        if("admin".equals(from)) {
            log.info("redirecting to admin page");
            response.sendRedirect("/member/login");
        }else{
            log.info("redirecting to login page");
            response.sendRedirect("/member/login");
        }


    }
}

