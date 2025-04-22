package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.CategoryDTO;
import kr.co.lotteOn.entity.Category;
import kr.co.lotteOn.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * 카테고리 전체 조회 (1차 + 2차 포함)
     */
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAllWithChildren();

        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * 카테고리 저장 (전체 삭제 후 재삽입 방식)
     */
    @Transactional
    public void saveAll(List<CategoryDTO> dtos) {
        System.out.println("💾 저장할 카테고리 수: " + dtos.size());
        categoryRepository.deleteAll();

        for (CategoryDTO parentDto : dtos) {
            Category parent = Category.builder()
                    .name(parentDto.getName())
                    .depth(1)
                    .sortOrder(0)
                    .useYN("Y")
                    .build();
            System.out.println("▶ 저장할 1차: " + parent.getName());

            if (parentDto.getChildren() != null) {
                for (CategoryDTO childDto : parentDto.getChildren()) {
                    Category child = Category.builder()
                            .name(childDto.getName())
                            .depth(2)
                            .sortOrder(0)
                            .useYN("Y")
                            .parent(parent)
                            .build();
                    parent.getChildren().add(child);
                    System.out.println("  - 2차 추가: " + child.getName());
                }
            }
            categoryRepository.save(parent); // Cascade 옵션으로 children도 같이 저장
        }
    }

    private CategoryDTO convertToDTO(Category entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(entity.getCategoryId());
        dto.setName(entity.getName());
        dto.setDepth(entity.getDepth());
        dto.setSortOrder(entity.getSortOrder());
        dto.setUseYN(entity.getUseYN());
        dto.setParentId(entity.getParent() != null ? entity.getParent().getCategoryId() : null);

        if (entity.getChildren() != null && !entity.getChildren().isEmpty()) {
            List<CategoryDTO> children = entity.getChildren().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            dto.setChildren(children);
        }

        return dto;
    }
}
