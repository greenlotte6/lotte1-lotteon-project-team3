package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Seller;
import kr.co.lotteOn.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Integer> {

Optional<Shop> findById(Integer shopId);

    List<Shop> seller(Seller seller);

    @Modifying
    @Query("DELETE FROM Shop s WHERE s.seller.sellerId IN :sellerIds")
    void deleteBySellerIds(@Param("sellerIds") List<String> sellerIds);


    List<Shop> findBySeller(Seller seller);
}
