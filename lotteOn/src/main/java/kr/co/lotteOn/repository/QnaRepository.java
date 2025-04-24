package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Qna;
import kr.co.lotteOn.repository.custom.QnaRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Integer> , QnaRepositoryCustom {
    public List<Qna> findTop5ByOrderByRegDateDesc();
}
