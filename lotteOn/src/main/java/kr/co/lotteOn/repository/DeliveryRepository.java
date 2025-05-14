package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Delivery;
import kr.co.lotteOn.repository.custom.DeliveryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer>, DeliveryRepositoryCustom {
}
