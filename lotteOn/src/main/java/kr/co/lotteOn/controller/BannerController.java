package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.BannerDTO;
import kr.co.lotteOn.entity.Banner;
import kr.co.lotteOn.repository.BannerRepository;
import kr.co.lotteOn.service.BannerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BannerController {

    private final BannerRepository bannerRepository;
    private final ModelMapper modelMapper;
    private final BannerService bannerService;

    @PostMapping("/admin/config/banners")
    public String createBanner(@ModelAttribute BannerDTO bannerDTO) throws IOException {
        // 이미지 저장
        String fileName = bannerDTO.getImageFile().getOriginalFilename();
        Path filePath = Paths.get("src/main/resources/static/images/banners", fileName);
        Files.copy(bannerDTO.getImageFile().getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Banner banner = modelMapper.map(bannerDTO, Banner.class);
        banner.setImagePath("/images/banners/" + fileName);
        banner.setActive(false);

        banner.setSliderTitle(bannerDTO.getSliderTitle());
        banner.setSliderSubTitle(bannerDTO.getSliderSubTitle());

        bannerService.deleteBannerCache();

        bannerRepository.save(banner);

        List<BannerDTO> list = bannerService.registerCache();

        return "redirect:/admin/config/banner";
    }

    @PostMapping("/banners/activate/{id}")
    public String activateBanner(@PathVariable int id) {
        bannerService.activateBanner(id);
        return "redirect:/admin/config/banner";
    }

    @PostMapping("/banners/deactivate/{id}")
    public String deactivateBanner(@PathVariable int id) {
        bannerService.deactivateBanner(id);
        return "redirect:/admin/config/banner";
    }

    @PostMapping("/banners/delete")
    @ResponseBody
    public ResponseEntity<?> deleteBanners(@RequestBody List<Integer> bannerIds) {
        log.info("bannerIds = {}", bannerIds);
        if (bannerIds.isEmpty()) {
            return ResponseEntity.badRequest().body("삭제할 항목이 없습니다.");
        }
        bannerService.deleteByIdIn(bannerIds);
        return ResponseEntity.ok().build();
    }
}
