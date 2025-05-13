package kr.co.lotteOn.repository;

import kr.co.lotteOn.dto.SalesDTO;
import kr.co.lotteOn.entity.Sales;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Integer> {

    //    @Query("SELECT new kr.co.lotteOn.dto.SalesDTO(s.salesNo, s.seller.companyName, s.seller.businessNo) " +
//            "FROM Sales s")
    @Query("SELECT new kr.co.lotteOn.dto.SalesDTO(s.salesNo, s.seller.companyName, s.seller.businessNo) FROM Sales s LEFT JOIN s.seller")
    List<SalesDTO> findAllSales();

    @Query(value = "SELECT s.companyName, SUM(oi.quantity) AS orderCount " +
            "FROM OrderItem oi " +
            "JOIN Product p ON oi.productCode = p.productCode " +
            "JOIN Seller s ON p.companyName = s.companyName " +
            "GROUP BY s.companyName", nativeQuery = true)
    List<Object[]> findOrderCountsBySeller();



}
