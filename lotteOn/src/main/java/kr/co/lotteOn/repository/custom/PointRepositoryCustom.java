package kr.co.lotteOn.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.lotteOn.dto.point.PointPageRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PointRepositoryCustom {
    public Page<Tuple> searchPoint(PointPageRequestDTO pageRequestDTO, Pageable pageable);
}
