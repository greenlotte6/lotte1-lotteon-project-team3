package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Story;
import kr.co.lotteOn.repository.custom.StoryRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Integer>, StoryRepositoryCustom {
    public List<Story> findTop5ByOrderByRegDateDesc();

    public Page<Story> findAllByCate(String cate, Pageable pageable);
}
