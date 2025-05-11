package kr.co.lotteOn.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.lotteOn.entity.Banner;
import kr.co.lotteOn.entity.Config;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.service.BannerService;
import kr.co.lotteOn.service.ConfigService;
import kr.co.lotteOn.service.MemberService;
import kr.co.lotteOn.service.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@Component
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttributeAdvice {

    private final VersionService versionService;
    private final ConfigService configService;
    private final BannerService bannerService;
    private final MemberService memberService;

    @ModelAttribute("selectedVersionId")
    public String addLatestVersionIdToModel() {
        return versionService.getLatestVersionId();
    }

    @ModelAttribute("config")
    public Config config() {
        return configService.findById(1);
    }

    @ModelAttribute("mainTopBanner")
    public Banner mainTopBanner() {
        return bannerService.findByLocation("MAIN1");
    }

    @ModelAttribute("loginUser")
    public Member loginUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            String id = auth.getName();
            System.out.println(">>> 로그인 사용자 ID: " + id);
            return memberService.findById(id).orElse(null);
        }
        System.out.println(">>> 로그인 안됨");
        return null;
    }
}
