package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.ConfigDTO;
import kr.co.lotteOn.service.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class ConfigController {

    private final ConfigService configService;

    @PostMapping("/config/site")
    public String updateSiteInfo(@ModelAttribute ConfigDTO configDTO) {
        configService.updateSiteInfo(configDTO);
        return "redirect:/admin/config/basic";
    }

    @PostMapping("/config/logo")
    public String updateLogos(@RequestParam("headerLogo") MultipartFile headerLogo,
                              @RequestParam("footerLogo") MultipartFile footerLogo,
                              @RequestParam("favicon") MultipartFile favicon) {
        try {
            configService.updateLogos(headerLogo, footerLogo, favicon);  // 로고 파일 업데이트
        } catch (IOException e) {
            e.printStackTrace();  // 파일 업로드 실패시 예외 처리
            return null;
        }
        return "redirect:/admin/config/basic";  // 성공 시 설정 페이지로
    }

    @PostMapping("/config/company")
    public String updateCompanyInfo(@ModelAttribute ConfigDTO configDTO) {
        configService.updateCompanyInfo(configDTO);
        return "redirect:/admin/config/basic";
    }

    @PostMapping("/config/customerService")
    public String updateCustomerServiceInfo(@ModelAttribute ConfigDTO configDTO) {
        configService.updateCustomerServiceInfo(configDTO);
        return "redirect:/admin/config/basic";
    }

    @PostMapping("/config/copyright")
    public String updateCopyright(@ModelAttribute ConfigDTO configDTO) {
        configService.updateCopyright(configDTO);
        return "redirect:/admin/config/basic";
    }
}
