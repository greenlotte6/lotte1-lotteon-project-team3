package kr.co.lotteOn.repository.custom;

import kr.co.lotteOn.dto.notice.NoticePageRequestDTO;
import kr.co.lotteOn.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoticeRepositoryCustom {

    public Page<Notice> searchAllForList(Pageable pageable);
    public Page<Notice> searchAllForCate(NoticePageRequestDTO noticePageRequestDTO, Pageable pageable);
}
