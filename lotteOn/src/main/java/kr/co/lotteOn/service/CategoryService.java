package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.CategoryDTO;
import kr.co.lotteOn.entity.Category;
import kr.co.lotteOn.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAllWithChildren();

        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * 카테고리 저장 (병합 방식: 삭제 X)
     */
    @Transactional
    public void saveAll(List<CategoryDTO> dtos) {
        log.info("💾 카테고리 저장 요청 수: {}", dtos.size());

        List<Category> existing = categoryRepository.findAllWithChildren();
        Map<Long, Category> existingMap = existing.stream()
                .filter(c -> c.getCategoryId() != null)
                .collect(Collectors.toMap(Category::getCategoryId, c -> c));

        Set<Long> updatedIds = new HashSet<>();

        for (CategoryDTO parentDto : dtos) {
            // parent 매핑
            Category parent = Optional.ofNullable(parentDto.getCategoryId())
                    .map(existingMap::get)
                    .orElseGet(() -> existing.stream()
                            .filter(c -> c.getDepth() == 1 && c.getName().equals(parentDto.getName()))
                            .findFirst()
                            .orElse(Category.builder()
                                    .depth(1)
                                    .useYN("Y")
                                    .children(new ArrayList<>())
                                    .build()));

            parent.setName(parentDto.getName());
            parent.setSortOrder(parentDto.getSortOrder());
            parent.setUseYN("Y");

            // 자식 처리
            List<Category> oldChildren = parent.getChildren() != null ? parent.getChildren() : new ArrayList<>();
            List<Category> newChildren = new ArrayList<>();
            Set<String> incomingChildNames = new HashSet<>();

            if (parentDto.getChildren() != null) {
                for (CategoryDTO childDto : parentDto.getChildren()) {
                    Category child = oldChildren.stream()
                            .filter(c -> c.getName().equals(childDto.getName()))
                            .findFirst()
                            .orElse(Category.builder()
                                    .depth(2)
                                    .useYN("Y")
                                    .parent(parent)
                                    .build());

                    child.setName(childDto.getName());
                    child.setSortOrder(childDto.getSortOrder());
                    child.setUseYN("Y");

                    newChildren.add(child);
                    incomingChildNames.add(child.getName());

                    if (child.getCategoryId() != null) {
                        updatedIds.add(child.getCategoryId());
                    }
                }
            }

            // 기존 자식 중에서 누락된 건 useYN = 'N'
            for (Category oldChild : oldChildren) {
                if (!incomingChildNames.contains(oldChild.getName())) {
                    oldChild.setUseYN("N");
                    newChildren.add(oldChild);
                }
            }

            parent.setChildren(newChildren);
            Category savedParent = categoryRepository.save(parent);

            if (savedParent.getCategoryId() != null) {
                updatedIds.add(savedParent.getCategoryId());
            }
        }

        // 기존 부모 중 누락된 것들 useYN = 'N'
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
}

