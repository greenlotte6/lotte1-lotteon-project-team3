package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Notice;
import kr.co.lotteOn.repository.custom.NoticeRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer>, NoticeRepositoryCustom {
    public List<Notice> findTop5ByOrderByRegDateDesc();

    public Page<Notice> findAllByCate(String cate, Pageable pageable);
}
