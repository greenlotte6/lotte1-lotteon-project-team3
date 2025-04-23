package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Integer> {

    boolean existsByShopId(int shopId);
}
