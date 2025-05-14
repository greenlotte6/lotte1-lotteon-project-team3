package kr.co.lotteOn.service;

import jakarta.persistence.EntityNotFoundException;
import kr.co.lotteOn.entity.Banner;
import kr.co.lotteOn.entity.Config;
import kr.co.lotteOn.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    public void activateBanner(int id) {
        Banner banner = bannerRepository.findById(id).orElseThrow();
        banner.setActive(true);
        bannerRepository.save(banner);
    }

    public void deactivateBanner(int id) {
        Banner banner = bannerRepository.findById(id).orElseThrow();
        banner.setActive(false);
        bannerRepository.save(banner);
    }

    public List<Banner> findValidBanner(String location) {
        List<Banner> banners = bannerRepository.findByLocation(location);
        if (banners == null || banners.isEmpty()) {
            return Collections.emptyList(); // 없으면 빈 리스트 반환
        }

        LocalDateTime now = LocalDateTime.now();

        // 유효한 배너만 필터링
        return banners.stream()
                .filter(Banner::isActive) // active = true
                .filter(b -> {
                    LocalDateTime start = LocalDateTime.of(b.getStartDate(), b.getStartTime());
                    LocalDateTime end = LocalDateTime.of(b.getEndDate(), b.getEndTime());
                    return !now.isBefore(start) && !now.isAfter(end); // 시간 범위 내
                })
                .collect(Collectors.toList());
    }
}
