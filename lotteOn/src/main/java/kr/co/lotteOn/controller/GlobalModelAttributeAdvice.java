package kr.co.lotteOn.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.lotteOn.entity.Banner;
import kr.co.lotteOn.entity.Config;
import kr.co.lotteOn.service.BannerService;
import kr.co.lotteOn.service.ConfigService;
import kr.co.lotteOn.service.VersionService;
import lombok.RequiredArgsConstructor;
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
}
