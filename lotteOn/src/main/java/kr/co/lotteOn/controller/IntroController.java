package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.recruit.RecruitDTO;
import kr.co.lotteOn.dto.recruit.RecruitPageRequestDTO;
import kr.co.lotteOn.dto.recruit.RecruitPageResponseDTO;
import kr.co.lotteOn.dto.story.StoryDTO;
import kr.co.lotteOn.dto.story.StoryPageRequestDTO;
import kr.co.lotteOn.dto.story.StoryPageResponseDTO;
import kr.co.lotteOn.entity.Story;
import kr.co.lotteOn.service.AdminCSService;
import kr.co.lotteOn.service.IntroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/intro")
@Controller
public class IntroController {

    private final IntroService introService;
    private final AdminCSService adminCSService;

    //회사소개 - 메인
    @GetMapping("/intro_home")
    public String home(Model model){
        List<StoryDTO> stories = introService.findAllStoriesByLimit5();

        model.addAttribute("stories", stories);

        return "/intro/intro_home";
    }
    //회사소개 기업문화
    @GetMapping("/intro_culture")
    public String culture(){
        return "/intro/intro_culture";
    }
    //회사소개 - 채용
    @GetMapping("/intro_employ")
    public String employ(Model model, RecruitPageRequestDTO pageRequestDTO){
        RecruitPageResponseDTO pageResponseDTO = introService.recruitFindAll(pageRequestDTO);

        List<RecruitDTO> recruitList = pageResponseDTO.getDtoList();
        for (RecruitDTO recruit : recruitList) {
            recruit.setExperience(recruit.getExperienceYear());
        }

        model.addAttribute("page", pageResponseDTO);
        model.addAttribute("recruit", pageResponseDTO.getDtoList());

        return "/intro/intro_employ";
    }

    //회사소개 - 채용(검색)
    @GetMapping("/recruitSearch")
    public String employSearch(Model model, RecruitPageRequestDTO pageRequestDTO){
        RecruitPageResponseDTO pageResponseDTO = introService.recruitFindAllByCate(pageRequestDTO);

        List<RecruitDTO> recruitList = pageResponseDTO.getDtoList();
        for (RecruitDTO recruit : recruitList) {
            recruit.setExperience(recruit.getExperienceYear());
        }

        model.addAttribute("page", pageResponseDTO);

        return "/intro/intro_employ_search";
    }

    //회사소개 - 채용(글보기)
    @GetMapping("/intro_employ_view")
    public String employView(@RequestParam int recruitNo, Model model){
        RecruitDTO recruitDTO = introService.findRecruitById(recruitNo);

        recruitDTO.setExperience(recruitDTO.getExperienceYear());

        model.addAttribute("recruit", recruitDTO);

        return "/intro/intro_employ_view";
    }

    //회사소개 - 미디어
    @GetMapping("/intro_media")
    public String media(){
        return "/intro/intro_media";
    }
    //회사소개 - 소식과 이야기(전체)
    @GetMapping("/intro_story")
    public String story(Model model, StoryPageRequestDTO pageRequestDTO,
                        @RequestParam(name = "cate", required = false) String cate) {
        StoryPageResponseDTO pageResponseDTO;

        if (cate != null && !cate.isEmpty()) {
            // 카테고리가 있을 경우, 카테고리별 로직 실행
            pageResponseDTO = introService.storyFindAllByCate(pageRequestDTO, cate);
            model.addAttribute("cate", cate);
        } else {
            // 카테고리가 없을 경우, 전체 소식과 이야기 로직 실행
            pageResponseDTO = introService.storyFindAll(pageRequestDTO);
        }

        model.addAttribute("page", pageResponseDTO);
        model.addAttribute("story", pageResponseDTO.getDtoList());

        return "/intro/intro_story";
    }

    //회사소개 - 소식과 이야기 blog
    @GetMapping("/intro_story_view")
    public String story_view(int storyNo, Model model){
        StoryDTO storyDTO = introService.findById(storyNo);

        model.addAttribute("story", storyDTO);

        return "/intro/intro_story_view";
    }

    /* **************************회사소개 끝*********************************** */
}
