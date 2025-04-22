package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Product;
import kr.co.lotteOn.entity.ProductNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductNoticeRepository extends JpaRepository<ProductNotice, Long> {
    Optional<ProductNotice> findByProduct(Product product);
}
