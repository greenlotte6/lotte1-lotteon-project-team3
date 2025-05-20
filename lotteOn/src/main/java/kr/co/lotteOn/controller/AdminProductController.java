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

        int currentPage = products.getNumber();       // 현재 페이지
        int totalPages = products.getTotalPages();    // 전체 페이지 수
        int groupSize = 10;                            // 한 번에 보여줄 페이지 수

        int startPage = (currentPage / groupSize) * groupSize;
        int endPage = Math.min(startPage + groupSize, totalPages);

        model.addAttribute("products", products);
        model.addAttribute("searchField", searchField);
        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "/admin/product/list";
    }


    @PostMapping("/register")
    public String register(@ModelAttribute ProductDTO dto) {

        productService.deleteSearchCache();
        productService.deleteSortedProductByCate();
        productService.deleteGetBest10ProductsByCategories();
        productService.deleteGetBest10ProductsByCategoryId();
        productService.deleteGetSortedProductsByCategories();
        productService.deleteGetBest10DiscountedProducts();
        productService.deleteGetDiscountedProductsWithReview();
        productService.deleteGetSortedAllProducts();
        productService.deleteGetPopularProductsWithReview();

        productService.saveProduct(dto);
        return "redirect:/admin/product/list";
    }
    @PostMapping("/update")
    public String updateProduct(@ModelAttribute ProductDTO productDTO) {
        productService.modifyProduct(productDTO);
        return "redirect:/admin/product/list";
    }
}
