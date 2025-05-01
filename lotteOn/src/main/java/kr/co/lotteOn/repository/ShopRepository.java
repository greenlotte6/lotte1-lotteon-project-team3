package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Seller;
import kr.co.lotteOn.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Integer> {

Optional<Shop> findById(Integer shopId);

    List<Shop> seller(Seller seller);
}
