package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {

    List<Banner> findByLocation(String location);
    void deleteByIdIn(List<Integer> bannerIds);
}