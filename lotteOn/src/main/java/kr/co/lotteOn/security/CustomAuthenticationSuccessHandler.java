package kr.co.lotteOn.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();

        request.getSession().setAttribute("memberId", member.getId());

        String userType = request.getParameter("userType");
        request.getSession().setAttribute("userType", userType);

        // 최근 로그인한 날짜 업데이트를 위한 코드입니당
        String memberId = authentication.getName();
        memberService.updateVisitDate(memberId);

        if(authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))){
            response.sendRedirect("/admin/admin");
        } else {
            response.sendRedirect("/");
        }
    }
}
