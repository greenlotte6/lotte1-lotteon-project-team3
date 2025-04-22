package kr.co.lotteOn.repository.custom;


import kr.co.lotteOn.dto.faq.FaqPageRequestDTO;
import kr.co.lotteOn.entity.Faq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FaqRepositoryCustom {

    public Page<Faq> searchAllForList(Pageable pageable);
    public Page<Faq> searchAllForCate(FaqPageRequestDTO faqPageRequestDTO, Pageable pageable);


}
