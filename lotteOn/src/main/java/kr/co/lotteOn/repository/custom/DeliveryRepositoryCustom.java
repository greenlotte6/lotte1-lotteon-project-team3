package kr.co.lotteOn.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.lotteOn.dto.delivery.DeliveryPageRequestDTO;
import kr.co.lotteOn.dto.order.OrderPageRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepositoryCustom {

    public Page<Tuple> findAllByStatus(DeliveryPageRequestDTO pageRequestDTO, Pageable pageable);

    Page<Tuple> findAll(DeliveryPageRequestDTO pageRequestDTO, Pageable pageable);
}
