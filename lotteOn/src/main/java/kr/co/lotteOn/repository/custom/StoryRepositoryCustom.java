package kr.co.lotteOn.repository.custom;

import kr.co.lotteOn.dto.story.StoryPageRequestDTO;
import kr.co.lotteOn.entity.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoryRepositoryCustom {
    public Page<Story> searchAllForList(Pageable pageable);
    public Page<Story> searchAllForCate(StoryPageRequestDTO storyPageRequestDTO, Pageable pageable);
}
