package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.children WHERE c.depth = 1 ORDER BY c.sortOrder ASC")
    List<Category> findAllWithChildren();
    List<Category> findByParent_CategoryId(Long parentId);
}
