package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface VersionRepository extends JpaRepository<Version, String> {
    Optional<Version> findTopByOrderByRegDateDesc();
    List<Version> findAllByOrderByRegDateDesc();
    void deleteByVersionIdIn(List<String> versionIds);
}
