package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.ProductDTO;
import kr.co.lotteOn.repository.ProductRepository;
import kr.co.lotteOn.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class indexController {

    private final ProductService productService;

    @GetMapping("/")
    public String index(Model model) {
        List<ProductDTO> popularProducts = productService.getPopularProductsWithReview();
        List<ProductDTO> specialProducts = productService.getDiscountedProductsWithReview();

        model.addAttribute("popularProducts", popularProducts);
        model.addAttribute("specialProducts", specialProducts);

        return "/index";
    }
}
