package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.BannerDTO;
import kr.co.lotteOn.entity.Banner;
import kr.co.lotteOn.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RequiredArgsConstructor
@Controller
public class BannerController {

    private final BannerRepository bannerRepository;
    private final ModelMapper modelMapper;

    @PostMapping("/admin/config/banners")
    public String createBanner(@ModelAttribute BannerDTO bannerDTO) throws IOException {
        // 이미지 저장
        String fileName = bannerDTO.getImageFile().getOriginalFilename();
        Path filePath = Paths.get("src/main/resources/static/images/banners", fileName);
        Files.copy(bannerDTO.getImageFile().getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Banner banner = modelMapper.map(bannerDTO, Banner.class);
        banner.setImagePath("/images/banners/" + fileName);
        banner.setActive(false);

        bannerRepository.save(banner);
        return "redirect:/admin/config/banner";
    }
}
