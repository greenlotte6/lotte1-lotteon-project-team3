package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.ProductDTO;
import kr.co.lotteOn.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/product")
public class AdminProductApiController {

    private final ProductService productService;

    @GetMapping("/edit/code/{productCode}")
    public ResponseEntity<ProductDTO> getProductByCode(@PathVariable String productCode) {
        ProductDTO productDTO = productService.getProductByCode(productCode);
        return ResponseEntity.ok(productDTO);
    }

    @DeleteMapping("/delete/{productCode}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productCode) {
        productService.deleteProduct(productCode);
        return ResponseEntity.ok().build();
    }

}
