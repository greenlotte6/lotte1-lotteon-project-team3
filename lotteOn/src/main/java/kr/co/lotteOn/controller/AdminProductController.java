package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.ProductDTO;
import kr.co.lotteOn.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/product")
public class AdminProductController {

    private final ProductService productService;

    /*------------ 관리자 - 상품관리 ------------*/
// ✅ 상품 등록 (모달에서 JSON으로 전송됨)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody ProductDTO dto) {
        log.info("상품 등록 요청: {}", dto);

        try {
            productService.saveProduct(dto);
            return ResponseEntity.ok("등록 성공");
        } catch (Exception e) {
            log.error("상품 등록 실패", e);
            return ResponseEntity.internalServerError().body("등록 실패: " + e.getMessage());
        }
    }

}
