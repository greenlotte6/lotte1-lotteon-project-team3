package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.ProductDTO;
import kr.co.lotteOn.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/product")
public class AdminProductController {

    private final ProductService productService;

    /*------------ 관리자 - 상품관리 ------------*/

    //상품관리 - 목록
    @GetMapping("/list")
    public String productList(@RequestParam(required = false) String searchField,
                                @RequestParam(required = false) String searchKeyword,
                                @PageableDefault(size = 10) Pageable pageable,
                                Model model) {
        Page<ProductDTO> products = productService.searchProducts(searchField, searchKeyword, pageable);
        model.addAttribute("products", products);
        model.addAttribute("searchField", searchField);
        model.addAttribute("searchKeyword", searchKeyword);
        return "/admin/product/list";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute ProductDTO dto) {

        productService.saveProduct(dto);
        return "redirect:/admin/product/list";
    }
    @PostMapping("/update")
    public String updateProduct(@ModelAttribute ProductDTO productDTO) {
        productService.modifyProduct(productDTO);
        return "redirect:/admin/product/list";
    }
}
