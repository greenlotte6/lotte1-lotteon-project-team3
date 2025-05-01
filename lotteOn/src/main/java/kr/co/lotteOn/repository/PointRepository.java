package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Point;
import kr.co.lotteOn.repository.custom.PointRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<Point, Integer>, PointRepositoryCustom {
}
