package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.CategoryDTO;
import kr.co.lotteOn.entity.Category;
import kr.co.lotteOn.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    @Cacheable(value = "categoriesCache")
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAllWithChildren();

        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * 카테고리 저장 (병합 방식: 삭제 X)
     */
    @CacheEvict(value = "categoriesCache", allEntries = true)
    @Transactional
    public void saveAll(List<CategoryDTO> dtos) {

        // 1. 기존 전체 카테고리 + 자식 포함 조회
        List<Category> existing = categoryRepository.findAllWithChildren();

        Map<Long, Category> existingMap = existing.stream()
                .filter(c -> c.getCategoryId() != null)
                .collect(Collectors.toMap(Category::getCategoryId, c -> c));

        Set<Long> updatedIds = new HashSet<>();

        for (CategoryDTO parentDto : dtos) {
            Category parent = null;

            // 2. 기존 parent 찾기 (ID 우선, 없으면 이름 기반)
            if (parentDto.getCategoryId() != null) {
                parent = existingMap.get(parentDto.getCategoryId());
            }
            if (parent == null) {
                parent = existing.stream()
                        .filter(c -> c.getDepth() == 1 && c.getName().equals(parentDto.getName()))
                        .findFirst()
                        .orElse(Category.builder()
                                .depth(1)
                                .useYN("Y")
                                .children(new ArrayList<>())
                                .build());
            }

            parent.setName(parentDto.getName());
            parent.setSortOrder(parentDto.getSortOrder());
            parent.setUseYN("Y");

            // 3. 자식 카테고리 처리
            List<Category> oldChildren = parent.getChildren() != null ? parent.getChildren() : new ArrayList<>();
            List<Category> newChildren = new ArrayList<>();
            Set<Long> incomingChildIds = new HashSet<>();

            if (parentDto.getChildren() != null) {
                for (CategoryDTO childDto : parentDto.getChildren()) {
                    Category child = null;

                    if (childDto.getCategoryId() != null) {
                        child = oldChildren.stream()
                                .filter(c -> c.getCategoryId() != null && c.getCategoryId().equals(childDto.getCategoryId()))
                                .findFirst()
                                .orElse(null);
                    }

                    if (child == null) {
                        child = oldChildren.stream()
                                .filter(c -> c.getName().equals(childDto.getName()))
                                .findFirst()
                                .orElse(Category.builder()
                                        .depth(2)
                                        .useYN("Y")
                                        .parent(parent)
                                        .build());
                    }

                    child.setName(childDto.getName());
                    child.setSortOrder(childDto.getSortOrder());
                    child.setUseYN("Y");
                    child.setParent(parent);

                    newChildren.add(child);

                    if (child.getCategoryId() != null) {
                        updatedIds.add(child.getCategoryId());
                        incomingChildIds.add(child.getCategoryId());
                    }
                }
            }

            // 4. 기존 자식 중 누락된 건 비활성화
            for (Category oldChild : oldChildren) {
                if (oldChild.getCategoryId() != null && !incomingChildIds.contains(oldChild.getCategoryId())) {
                    oldChild.setUseYN("N");
                    newChildren.add(oldChild);
                }
            }

            parent.setChildren(newChildren);
            Category savedParent = categoryRepository.save(parent);

            for (Category child : newChildren) {
                categoryRepository.save(child);
            }

            if (savedParent.getCategoryId() != null) {
                updatedIds.add(savedParent.getCategoryId());
            }
        }

        // 5. 기존 parent 중 누락된 것들 useYN = 'N'
        for (Category cat : existing) {
            if (!updatedIds.contains(cat.getCategoryId())) {
                cat.setUseYN("N");
                categoryRepository.save(cat);
            }
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

    @Transactional
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}

