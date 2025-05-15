package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.VisitLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface VisitLogRepository extends JpaRepository<VisitLog, Long> {
    Optional<VisitLog> findByUriAndVisitDate(String uri, LocalDate visitDate);
}
