package kr.co.lotteOn.controller;

import jakarta.validation.Valid;
import kr.co.lotteOn.dto.MemberDTO;
import kr.co.lotteOn.dto.VersionDTO;
import kr.co.lotteOn.service.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class VersionController {

    private final VersionService versionService;

    @PostMapping("/config/version")
    public String register(@Valid @ModelAttribute VersionDTO versionDTO,
                           BindingResult result) {
        if (result.hasErrors()) {
            return "/admin/config/version";
        }
        versionService.registerVersion(versionDTO);
        return "redirect:/admin/config/version";
    }
}
