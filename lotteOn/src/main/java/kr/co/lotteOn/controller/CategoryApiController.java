package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.CategoryDTO;
import kr.co.lotteOn.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/config/category/api")
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;

    // ✅ 조회: 1차 + 2차 카테고리
    @GetMapping
    public List<CategoryDTO> getCategoryList() {
        return categoryService.getAllCategories();
    }

    // ✅ 저장: 전체 재등록 방식
    @PostMapping
    public void saveCategoryList(@RequestBody List<CategoryDTO> categories) {
        categoryService.saveAll(categories);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().build();
    }
}
