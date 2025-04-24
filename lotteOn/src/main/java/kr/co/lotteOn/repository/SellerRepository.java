package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, String> {

    boolean existsBySellerId(String sellerId);

    String sellerId(String sellerId);


}
