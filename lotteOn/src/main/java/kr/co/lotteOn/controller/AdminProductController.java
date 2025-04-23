package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.ProductDTO;
import kr.co.lotteOn.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/product")
public class AdminProductController {

    private final ProductService productService;

    /*------------ 관리자 - 상품관리 ------------*/

    //환경설정 - 카테고리 관리
    @GetMapping("/config/category")
    public String category() {
        return "/admin/config/category";
    }

    // ✅ 상품 등록 (모달에서 JSON으로 전송됨)
    @PostMapping("/register")
    public String register(@ModelAttribute ProductDTO dto) {

        System.out.println(dto.getCategoryId());
        System.out.println(dto.getCategoryId());
        System.out.println(dto.getCategoryId());
        System.out.println(dto.getCategoryId());
        System.out.println(dto.getCategoryId());

        productService.saveProduct(dto);
        return "redirect:/admin/product/list";
    }

}
