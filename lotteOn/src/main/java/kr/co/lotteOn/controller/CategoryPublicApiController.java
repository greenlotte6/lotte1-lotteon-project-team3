package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.CategoryDTO;
import kr.co.lotteOn.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/categories")
public class CategoryPublicApiController {

    private final CategoryService categoryService;

    // 🧩 카테고리 조회 (고객용)
    @GetMapping
    public List<CategoryDTO> getCategories() {
        return categoryService.getAllCategories();
    }
}
