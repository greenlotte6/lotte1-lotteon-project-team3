package kr.co.lotteOn.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.lotteOn.dto.order.OrderPageRequestDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderRepositoryCustom {
    public Page<Tuple> findAllByMember_Id(OrderPageRequestDTO pageRequestDTO, Pageable pageable);

    public Page<Tuple> findTop3ByMemberOrderByOrderDateDesc(Member memberId);

    public Page<Tuple> findAll(OrderPageRequestDTO pageRequestDTO, Pageable pageable);

    public Page<Tuple> findAllByStatus(OrderPageRequestDTO pageRequestDTO, Pageable pageable);
}
