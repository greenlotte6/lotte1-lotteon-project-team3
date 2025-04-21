package kr.co.lotteOn.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.lotteOn.dto.NoticePageRequestDTO;
import kr.co.lotteOn.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepositoryCustom {

    public Page<Notice> searchAllForList(Pageable pageable);
    public Page<Tuple> searchAllForSearch(NoticePageRequestDTO noticePageRequestDTO,Pageable pageable);

}
