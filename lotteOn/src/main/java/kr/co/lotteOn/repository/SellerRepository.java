package kr.co.lotteOn.repository;

import jakarta.transaction.Transactional;
import kr.co.lotteOn.entity.Seller;
import org.apache.ibatis.annotations.Param;
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


    void deleteById(String sellerId);
}
