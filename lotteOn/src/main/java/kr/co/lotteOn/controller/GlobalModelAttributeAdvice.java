package kr.co.lotteOn.controller;

import kr.co.lotteOn.entity.Banner;
import kr.co.lotteOn.entity.Config;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.SearchKeyword;
import kr.co.lotteOn.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Component
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttributeAdvice {

    private final VersionService versionService;
    private final ConfigService configService;
    private final BannerService bannerService;
    private final MemberService memberService;
    private final SearchKeywordService searchKeywordService;

    @ModelAttribute("selectedVersionId")
    public String addLatestVersionIdToModel() {
        return versionService.getLatestVersionId();
    }

    @ModelAttribute("config")
    public Config config() {
        return configService.findById(1);
    }

    @ModelAttribute("mainTopBanner")
    public List<Banner> mainTopBanner() {
        return bannerService.findValidBanner("MAIN1");
    }
    @ModelAttribute("swiperBanner")
    public List<Banner> swiperBanner() {
        return bannerService.findValidBanner("SLIDER");
    }
    @ModelAttribute("productBanner")
    public List<Banner> productBanner() {
        return bannerService.findValidBanner("PRODUCT");
    }
    @ModelAttribute("loginBanner")
    public List<Banner> loginBanner() {
        return bannerService.findValidBanner("LOGIN");
    }
    @ModelAttribute("myPageBanner")
    public List<Banner> myPageBanner() {
        return bannerService.findValidBanner("MYPAGE");
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

    @ModelAttribute("topKeywords")
    public List<SearchKeyword> getTopKeywords() {
        return searchKeywordService.getTopKeywords();
    }
}
