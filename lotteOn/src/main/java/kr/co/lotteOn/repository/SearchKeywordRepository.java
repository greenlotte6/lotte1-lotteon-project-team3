package kr.co.lotteOn.repository;

import jakarta.transaction.Transactional;
import kr.co.lotteOn.entity.SearchKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchKeywordRepository extends JpaRepository<SearchKeyword, Long> {

    Optional<SearchKeyword> findByKeyword(String keyword);

    @Query("SELECT sk FROM SearchKeyword sk ORDER BY sk.count DESC LIMIT 10")
    List<SearchKeyword> findTopKeywords();

    @Modifying
    @Transactional
    @Query("UPDATE SearchKeyword sk SET sk.count = 0")
    void resetAllCounts();
}