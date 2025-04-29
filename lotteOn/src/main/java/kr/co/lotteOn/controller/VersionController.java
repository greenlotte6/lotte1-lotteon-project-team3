package kr.co.lotteOn.controller;

import jakarta.validation.Valid;
import kr.co.lotteOn.dto.VersionDTO;
import kr.co.lotteOn.service.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/config/version")
    public String versionList(Model model) {
        List<VersionDTO> versionList = versionService.getAllVersions();
        model.addAttribute("versionList", versionList);
        return "/admin/config/version";
    }

    @PostMapping("/version/delete")
    @ResponseBody
    public ResponseEntity<?> deleteVersions(@RequestBody List<String> versionIds) {
        if (versionIds.isEmpty()) {
            return ResponseEntity.badRequest().body("삭제할 항목이 없습니다.");
        }
        versionService.deleteVersionsByIds(versionIds);
        return ResponseEntity.ok().build();
    }
}
