package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Product;
import kr.co.lotteOn.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    List<ProductOption> findByProduct(Product product);
}
