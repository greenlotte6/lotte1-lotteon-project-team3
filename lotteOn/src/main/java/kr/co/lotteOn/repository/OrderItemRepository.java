package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.OrderItem;
import kr.co.lotteOn.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findByOrder_OrderCode(String orderCode);

    OrderItem findFirstByOrder_OrderCode(String orderCode);


    @Query("SELECT oi.product.productCode FROM OrderItem oi GROUP BY oi.product.productCode ORDER BY COUNT(oi.product.productCode) DESC")
    List<String> findTopPopularProductCodes(Pageable pageable);


    @Query("""
    SELECT oi.product.productCode
    FROM OrderItem oi
    WHERE oi.product.category.categoryId = :categoryId
    GROUP BY oi.product.productCode
    ORDER BY COUNT(oi.product.productCode) DESC
""")
    List<String> findTopPopularProductCodesByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query("""
    SELECT oi.product.productCode
    FROM OrderItem oi
    WHERE oi.product.category.categoryId IN :categoryIds
    GROUP BY oi.product.productCode
    ORDER BY COUNT(oi.product.productCode) DESC
""")
    List<String> findTopPopularProductCodesByCategoryIds(@Param("categoryIds") List<Long> categoryIds, Pageable pageable);


    //상점 매출현황 추가
    @Query("""
        SELECT SUM (oi.quantity)
        FROM OrderItem oi
        JOIN oi.product p
        WHERE p.companyName= :companyName
""")
    Integer sumQuantityByCompanyName(@Param("companyName") String companyName);


    @Query("SELECT SUM(oi.price * oi.quantity) FROM OrderItem oi " +
            "JOIN oi.product p " +
            "WHERE p.companyName = :companyName")
    Integer sumOrderPriceByCompanyName(@Param("companyName") String companyName);

    @Query("SELECT SUM(i.total) FROM OrderItem i " +
            "JOIN i.product p " +
            "WHERE p.companyName = :companyName")
    Integer sumsalesTotalByCompanyName(@Param("companyName") String companyName);

}
