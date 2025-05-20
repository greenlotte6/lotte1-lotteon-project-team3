package kr.co.lotteOn.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import kr.co.lotteOn.dto.BannerDTO;
import kr.co.lotteOn.entity.Banner;
import kr.co.lotteOn.entity.Config;
import kr.co.lotteOn.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;
    private final ModelMapper modelMapper;

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

    @Transactional
    public void deleteByIdIn(List<Integer> bannerIds) {
        log.info("bannerIds Service {} ", bannerIds);
        bannerRepository.deleteByIdIn(bannerIds);
    }

    @CacheEvict(value = "deleteBannerCache", allEntries = true)
    public void deleteBannerCache() {}

    @Cacheable(value = "deleteBannerCache")
    public List<BannerDTO> registerCache() {
        List<Banner> banners = bannerRepository.findAll();

        List<BannerDTO> list = new ArrayList<>();
        for(Banner banner : banners) {
            BannerDTO bannerDTO = modelMapper.map(banner, BannerDTO.class);
            list.add(bannerDTO);
        }

        return list;
    }
}
