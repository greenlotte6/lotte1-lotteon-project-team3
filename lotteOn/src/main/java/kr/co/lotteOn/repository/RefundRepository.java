package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Refund;
import kr.co.lotteOn.repository.custom.RefundRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Integer> , RefundRepositoryCustom {
    List<Refund> findAllByMember_Id(String member_id, Pageable pageable);

    List<Refund> findTop3ByMember_IdOrderByRegDateDesc(String memberId);

    Page<Refund> findAll(Pageable pageable);

    Refund findByOrderCode(String orderCode);
}
