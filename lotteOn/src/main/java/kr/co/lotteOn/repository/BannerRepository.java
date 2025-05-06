package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {

    public Banner findByLocation(String location);
}