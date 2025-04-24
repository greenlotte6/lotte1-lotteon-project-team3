package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductCode(String productCode);
    Page<Product> findByNameContaining(String keyword, Pageable pageable);
    Page<Product> findByProductCodeContaining(String keyword, Pageable pageable);
    Page<Product> findByCompanyNameContaining(String keyword, Pageable pageable);
    Page<Product> findByMakerContaining(String keyword, Pageable pageable);
    Page<Product> findAll(Pageable pageable);
}
