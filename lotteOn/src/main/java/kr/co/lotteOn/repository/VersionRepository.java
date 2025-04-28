package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VersionRepository extends JpaRepository<Version, String> {

}
