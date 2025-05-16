package kr.co.lotteOn.repository;

import com.querydsl.core.Tuple;
import kr.co.lotteOn.dto.review.ReviewPageRequestDTO;
import kr.co.lotteOn.entity.Review;
import kr.co.lotteOn.repository.custom.ReviewRepositoryCustom;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>, ReviewRepositoryCustom {

    List<Review> findByProductCode(String productCode);
    List<Review> findByProductCodeOrderByRegDateDesc(String productCode);

    @Query("SELECT COALESCE(AVG(CAST(r.rating AS double)), 0) FROM Review r WHERE r.productCode = :productCode")
    double getAverageRatingByProductCode(@Param("productCode") String productCode);

}
