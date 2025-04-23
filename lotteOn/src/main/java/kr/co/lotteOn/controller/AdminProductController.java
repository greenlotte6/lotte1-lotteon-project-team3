package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.ProductDTO;
import kr.co.lotteOn.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public String productList(Model model) {
        List<ProductDTO> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "/admin/product/list";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute ProductDTO dto) {

        productService.saveProduct(dto);
        return "redirect:/admin/product/list";
    }

    //수정
    @GetMapping("/edit/{id}")
    @ResponseBody
    public ProductDTO getProductForEdit(@PathVariable Long id) {
        return productService.getProductById(id);
    }
    @PostMapping("/update")
    public String updateProduct(@ModelAttribute ProductDTO productDTO) {
        productService.modifyProduct(productDTO);
        return "redirect:/admin/product/list";
    }

}
