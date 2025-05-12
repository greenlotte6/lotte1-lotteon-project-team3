package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Return;
import kr.co.lotteOn.repository.custom.ReturnRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnRepository extends JpaRepository<Return, Integer> , ReturnRepositoryCustom {
}
