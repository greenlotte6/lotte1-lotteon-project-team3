package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
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


}
