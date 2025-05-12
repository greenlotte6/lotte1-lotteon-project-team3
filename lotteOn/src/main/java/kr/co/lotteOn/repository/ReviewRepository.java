package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByProductCode(String productCode);
}
