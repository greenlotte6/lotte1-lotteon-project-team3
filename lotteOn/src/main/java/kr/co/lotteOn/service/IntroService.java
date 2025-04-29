package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.recruit.RecruitDTO;
import kr.co.lotteOn.dto.recruit.RecruitPageRequestDTO;
import kr.co.lotteOn.dto.recruit.RecruitPageResponseDTO;
import kr.co.lotteOn.dto.story.StoryDTO;
import kr.co.lotteOn.dto.story.StoryPageRequestDTO;
import kr.co.lotteOn.dto.story.StoryPageResponseDTO;
import kr.co.lotteOn.entity.Recruit;
import kr.co.lotteOn.entity.Story;
import kr.co.lotteOn.repository.RecruitRepository;
import kr.co.lotteOn.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class IntroService {
    private final ModelMapper modelMapper;
    private final StoryRepository storyRepository;
    private final RecruitRepository recruitRepository;

    //회사소개 HOME - 글 출력하기(5개)
    public List<StoryDTO> findAllStoriesByLimit5(){
        List<Story> story = storyRepository.findTop5ByOrderByRegDateDesc();
        List<StoryDTO> storyDTOList = new ArrayList<>();
        for (Story story1 : story) {
            StoryDTO storyDTO = modelMapper.map(story1, StoryDTO.class);
            storyDTOList.add(storyDTO);
        }

        return storyDTOList;
    }

    //회사소개 소식과이야기 - 글 출력하기 (카테고리)
    public StoryPageResponseDTO storyFindAllByCate(StoryPageRequestDTO pageRequestDTO, String cate){
        // 기존 Pageable을 그대로 사용하지 않고, 이 메소드 내에서 새로 설정
        Pageable pageable = PageRequest.of(0, 1000, Sort.by("storyNo").descending());

        Page<Story> pageStory = storyRepository.findAllByCate(cate, pageable);

        List<StoryDTO> storyList = pageStory
                .getContent()
                .stream()
                .map(story -> {
                    StoryDTO storyDTO = modelMapper.map(story, StoryDTO.class);

                    storyDTO.setCate(story.getCate());
                    storyDTO.setWriter(story.getWriter().getId());

                    return storyDTO;
                }).toList();

        int total = (int) pageStory.getTotalElements();

        return StoryPageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(storyList)
                .total(total)
                .build();
    }

    public StoryPageResponseDTO storyFindAll(StoryPageRequestDTO pageRequestDTO){
        // 기존 Pageable을 그대로 사용하지 않고, 이 메소드 내에서 새로 설정
        Pageable pageable = PageRequest.of(0, 1000, Sort.by("storyNo").descending());

        Page<Story> pageStory = storyRepository.findAll(pageable);

        List<StoryDTO> storyList = pageStory
                .getContent()
                .stream()
                .map(story -> {
                    StoryDTO storyDTO = modelMapper.map(story, StoryDTO.class);
                    storyDTO.setCate(story.getCate());
                    storyDTO.setWriter(story.getWriter().getId());
                    return storyDTO;
                }).toList();

        int total = (int) pageStory.getTotalElements();

        return StoryPageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(storyList)
                .total(total)
                .build();

    }

    //회사소개 소식과이야기 - 글보기(뷰)
    public StoryDTO findById(int storyNo){
        Optional<Story> optStory = storyRepository.findById(storyNo);
        if (optStory.isPresent()) {
            Story story = optStory.get();
            StoryDTO storyDTO = modelMapper.map(story, StoryDTO.class);
            return storyDTO;
        }

        return null;
    }

    //채용하기 - 리스트
    public RecruitPageResponseDTO recruitFindAll(RecruitPageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.getPageable("recruitNo");

        Page<Recruit> pageRecruit = recruitRepository.searchAllForList(pageable);
        List<RecruitDTO> recruitDTOList = pageRecruit
                .getContent()
                .stream()
                .map(recruit -> {
                    RecruitDTO recruitDTO = modelMapper.map(recruit, RecruitDTO.class);
                    recruitDTO.setCate(recruit.getCate());
                    recruitDTO.setWriter(recruit.getWriter().getId());
                    return recruitDTO;
                }).toList();
        int total = (int) pageRecruit.getTotalElements();

        return RecruitPageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(recruitDTOList)
                .total(total)
                .build();
    }

    public RecruitPageResponseDTO recruitFindAllByCate(RecruitPageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.getPageable("recruitNo");

        Page<Recruit> pageRecruit = recruitRepository.searchAllForCate(pageRequestDTO,pageable);

        List<RecruitDTO> recruitList = pageRecruit
                .getContent()
                .stream()
                .map(recruit -> {
                    RecruitDTO recruitDTO = modelMapper.map(recruit, RecruitDTO.class);

                    recruitDTO.setWriter(recruit.getWriter().getId());
                    return recruitDTO;
                }).toList();

        int total = (int) pageRecruit.getTotalElements();

        return RecruitPageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(recruitList)
                .total(total)
                .build();

    }

    //채용하기 - 뷰

    public RecruitDTO findRecruitById(int recruitNo){
        Optional<Recruit> optRecruit = recruitRepository.findById(recruitNo);
        if (optRecruit.isPresent()) {
            Recruit recruit = optRecruit.get();
            RecruitDTO recruitDTO = modelMapper.map(recruit, RecruitDTO.class);
            recruitDTO.setCate(recruit.getCate());
            recruitDTO.setWriter(recruit.getWriter().getId());

            return recruitDTO;
        }
        return null;
    }
}
