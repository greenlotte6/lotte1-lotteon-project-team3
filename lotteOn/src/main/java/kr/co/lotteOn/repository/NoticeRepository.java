package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Notice;
import kr.co.lotteOn.repository.custom.NoticeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer>, NoticeRepositoryCustom {
}
