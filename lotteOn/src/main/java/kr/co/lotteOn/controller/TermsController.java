package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.TermsDTO;
import kr.co.lotteOn.service.TermsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/terms")
@RestController
@RequiredArgsConstructor
public class TermsController {

    private final TermsService termsService;

    @GetMapping("/api")
    public String getTermsContent(@RequestParam("type") String type) {
        return termsService.findTermsContentByType(type);
    }
}
