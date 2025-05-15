package kr.co.lotteOn.repository;

import kr.co.lotteOn.dto.qna.QnaPageRequestDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Qna;
import kr.co.lotteOn.repository.custom.QnaRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Integer> , QnaRepositoryCustom {
    public List<Qna> findTop5ByOrderByRegDateDesc();

    public Page<Qna> findAllByCate1(String cate1, Pageable pageable);

    public List<Qna> findTop3ByWriterOrderByRegDateDesc(Member writer);

    public int countByWriter_Id(String writerId);

    long countByRegDateBetween(LocalDateTime start, LocalDateTime end);
}
