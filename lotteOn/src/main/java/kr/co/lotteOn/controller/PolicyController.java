package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.TermsDTO;
import kr.co.lotteOn.service.TermsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/policy")
@Controller
public class PolicyController {

    private final TermsService termsService;

    @GetMapping("/policy")
    public String policy(Model model) {
        TermsDTO termsDTO = termsService.findTerms();

        model.addAttribute("termsDTO", termsDTO);

        return "/policy/policy";
    }
}
