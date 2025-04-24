package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Recruit;
import kr.co.lotteOn.repository.custom.RecruitRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitRepository extends JpaRepository<Recruit, Integer> , RecruitRepositoryCustom {
}
