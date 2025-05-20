package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductCode(String productCode);

    Page<Product> findByNameContaining(String keyword, Pageable pageable);

    Page<Product> findByProductCodeContaining(String keyword, Pageable pageable);

    Page<Product> findByCompanyNameContaining(String keyword, Pageable pageable);

    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.category WHERE p.productCode = :productCode")
    Optional<Product> findWithCategoryByProductCode(@Param("productCode") String productCode);

    List<Product> findByCategory_CategoryId(Long categoryId);

    //@Query("SELECT p FROM Product p LEFT JOIN FETCH p.options WHERE p.productCode IN :codes")
    //List<Product> findAllByProductCodeInWithOptions(@Param("codes") List<String> codes);

    List<Product> findByProductCodeIn(List<String> productCodes);

    @Query("""
                SELECT oi.product.productCode 
                FROM OrderItem oi 
                WHERE oi.product.category.categoryId = :categoryId
                GROUP BY oi.product.productCode 
                ORDER BY COUNT(oi.product.productCode) DESC
            """)
    List<String> findTopPopularProductCodesByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query("""
                SELECT DISTINCT p
                FROM Product p
                LEFT JOIN FETCH p.options
                LEFT JOIN FETCH p.notice
                LEFT JOIN FETCH p.category
                WHERE p.productCode IN :codes
            """)
    List<Product> findAllByProductCodeInWithOptions(@Param("codes") List<String> codes);

    @Query("""
                SELECT DISTINCT p
                FROM Product p
                LEFT JOIN FETCH p.options
                LEFT JOIN FETCH p.notice
                LEFT JOIN FETCH p.category
                ORDER BY (p.price * (1 - (p.discount / 100.0))) ASC
            """)
    List<Product> findAllOrderByDiscountedPriceAsc();

    @Query("""
                SELECT DISTINCT p
                FROM Product p
                LEFT JOIN FETCH p.options
                LEFT JOIN FETCH p.notice
                LEFT JOIN FETCH p.category
                ORDER BY (p.price * (1 - (p.discount / 100.0))) DESC
            """)
    List<Product> findAllOrderByDiscountedPriceDesc();

    @Query("""
                SELECT DISTINCT p
                FROM Product p
                LEFT JOIN FETCH p.options
                LEFT JOIN FETCH p.notice
                LEFT JOIN FETCH p.category
                ORDER BY p.id DESC
            """)
    List<Product> findAllWithFetchJoinOrderByIdDesc();

    @Query("""
                SELECT DISTINCT p
                FROM Product p
                LEFT JOIN FETCH p.options
                LEFT JOIN FETCH p.notice
                LEFT JOIN FETCH p.category
                WHERE p.category.categoryId = :categoryId
                ORDER BY (p.price * (1 - (p.discount / 100.0))) ASC
            """)
    List<Product> findByCategoryWithDiscountedPriceAsc(@Param("categoryId") Long categoryId);

    @Query("""
                SELECT DISTINCT p
                FROM Product p
                LEFT JOIN FETCH p.options
                LEFT JOIN FETCH p.notice
                LEFT JOIN FETCH p.category
                WHERE p.category.categoryId = :categoryId
                ORDER BY (p.price * (1 - (p.discount / 100.0))) DESC
            """)
    List<Product> findByCategoryWithDiscountedPriceDesc(@Param("categoryId") Long categoryId);

    @Query("""
                SELECT DISTINCT p
                FROM Product p
                LEFT JOIN FETCH p.options
                LEFT JOIN FETCH p.notice
                LEFT JOIN FETCH p.category
                WHERE p.category.categoryId = :categoryId
                ORDER BY p.id DESC
            """)
    List<Product> findWithFetchJoinByCategoryIdOrderByIdDesc(@Param("categoryId") Long categoryId);

    @Query("""
                SELECT p FROM Product p
                LEFT JOIN FETCH p.options
                LEFT JOIN FETCH p.notice
                LEFT JOIN FETCH p.category
                WHERE p.discount >= 20
            """)
    List<Product> findAllWithFetchJoinWhereDiscountOver20();

    List<Product> findByCategory_CategoryIdIn(List<Long> categoryIds);

    @Query("""
    SELECT oi.product.productCode
    FROM OrderItem oi
    WHERE oi.product.category.categoryId IN :categoryIds
    GROUP BY oi.product.productCode
    ORDER BY COUNT(oi.product.productCode) DESC
""")
    List<String> findTopPopularProductCodesByCategoryIds(@Param("categoryIds") List<Long> categoryIds, Pageable pageable);

    @Query("""
    SELECT DISTINCT p
    FROM Product p
    LEFT JOIN FETCH p.options
    LEFT JOIN FETCH p.notice
    LEFT JOIN FETCH p.category
    WHERE p.category.categoryId IN :categoryIds
""")
    List<Product> findAllByCategory_CategoryIdInWithOptions(@Param("categoryIds") List<Long> categoryIds);

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.views = p.views + 1 WHERE p.productCode = :productCode")
    void incrementViewByProductCode(@Param("productCode") String productCode);

    @Query("SELECT p FROM Product p JOIN FETCH p.category")
    List<Product> findAllWithFetchJoin();

    @EntityGraph(attributePaths = {"options", "notice", "category"})
    List<Product> findByNameContainingIgnoreCaseOrCompanyNameContainingIgnoreCase(String name, String companyName);

    List<Product> findTop10ByNameContainingIgnoreCase(String keyword);
}

