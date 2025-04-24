package kr.co.lotteOn.repository.custom;


import kr.co.lotteOn.dto.recruit.RecruitPageRequestDTO;
import kr.co.lotteOn.entity.Recruit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecruitRepositoryCustom {

    public Page<Recruit> searchAllForList(Pageable pageable);
    public Page<Recruit> searchAllForCate(RecruitPageRequestDTO recruitPageRequestDTO, Pageable pageable);


}
