package kr.co.lotteOn.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.lotteOn.dto.review.ReviewPageRequestDTO;
import kr.co.lotteOn.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {
    public Page<Tuple> findAllByMember_Id(ReviewPageRequestDTO pageRequestDTO, Pageable pageable);

    public Page<Tuple> findTop3ByMemberOrderByRegDateDesc(Member member);
}
