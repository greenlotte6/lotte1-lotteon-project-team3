package kr.co.lotteOn.config;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalAppInfoAdvice {

    private final AppInfo appInfo;

    @ModelAttribute("appName")
    public String appName() {
        return appInfo.getName();
    }

    @ModelAttribute("appVersion")
    public String appVersion() {
        return appInfo.getVersion();
    }
}