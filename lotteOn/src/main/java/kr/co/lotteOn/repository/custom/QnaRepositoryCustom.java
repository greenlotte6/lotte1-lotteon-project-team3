package kr.co.lotteOn.repository.custom;


import com.querydsl.core.Tuple;
import kr.co.lotteOn.dto.qna.QnaPageRequestDTO;
import kr.co.lotteOn.entity.Qna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QnaRepositoryCustom {

    public Page<Qna> searchAllForList(Pageable pageable);
    public Page<Qna> searchAllForCate(QnaPageRequestDTO qnaPageRequestDTO, Pageable pageable);


    public Page<Tuple> searchAllByWriter(QnaPageRequestDTO qnaPageRequestDTO, Pageable pageable);
}
