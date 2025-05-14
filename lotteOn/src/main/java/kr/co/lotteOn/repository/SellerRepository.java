package kr.co.lotteOn.repository;

import jakarta.transaction.Transactional;
import kr.co.lotteOn.dto.SalesDTO;
import kr.co.lotteOn.entity.Seller;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller, String> {

    boolean existsBySellerId(String sellerId);
    boolean existsByCompanyName(String companyName);
    boolean existsByBusinessNo(String businessNo);
    boolean existsByCommunicationNo(String communicationNo);
    boolean existsByHp(String hp);
    boolean existsByFax(String fax);



    List<SellerProjection> findAllBy();


    List<SellerProjection> findBySellerIdIn(List<String> sellerIds);

    Page<SellerProjection> findAllBy(Pageable pageable);



    @Query("SELECT s FROM Seller s WHERE "
            + "(:type= 'companyName' AND s.companyName LIKE %:keyword%) OR "
            + "(:type= 'delegate' AND s.delegate LIKE %:keyword%) OR "
            + "(:type= 'businessNo' AND s.businessNo LIKE %:keyword%) OR "
            + "(:type = 'hp' AND s.hp LIKE %:keyword%)")
    Page<SellerProjection> findBySearch(@Param("type") String type,
                                        @Param("keyword") String keyword,
                                        Pageable pageable);

    @Query("SELECT s FROM Seller s")
    Page<SellerProjection> findAllProjections(Pageable pageable);

    Seller findBySellerId(String sellerId);

    Seller findByCompanyName(String companyName);

    @Query("SELECT new kr.co.lotteOn.dto.SalesDTO(s.companyName, s.businessNo) FROM Seller s")
    List<SalesDTO> findAllSellerSales();
}
