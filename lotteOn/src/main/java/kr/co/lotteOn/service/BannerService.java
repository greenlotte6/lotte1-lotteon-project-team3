package kr.co.lotteOn.service;

import jakarta.persistence.EntityNotFoundException;
import kr.co.lotteOn.entity.Banner;
import kr.co.lotteOn.entity.Config;
import kr.co.lotteOn.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    public Banner findByLocation(String location) {
        return bannerRepository.findByLocation(location);
    }

    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }

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
}
