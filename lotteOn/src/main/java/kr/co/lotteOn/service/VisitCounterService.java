package kr.co.lotteOn.service;

import kr.co.lotteOn.entity.VisitLog;
import kr.co.lotteOn.repository.VisitLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class VisitCounterService {

    private final VisitLogRepository visitLogRepository;

    @Transactional
    public void logVisit(String uri) {
        LocalDate today = LocalDate.now();

        visitLogRepository.findByUriAndVisitDate(uri, today)
                .ifPresentOrElse(
                        log -> log.incrementCount(),
                        () -> visitLogRepository.save(
                                VisitLog.builder()
                                        .uri(uri)
                                        .visitDate(today)
                                        .count(1L)
                                        .build()
                        )
                );
    }

    public long getTodayVisitCount(String uri) {
        return visitLogRepository.findByUriAndVisitDate(uri, LocalDate.now())
                .map(VisitLog::getCount)
                .orElse(0L);
    }

    public long getYesterdayVisitCount(String uri) {
        return visitLogRepository.findByUriAndVisitDate(uri, LocalDate.now().minusDays(1))
                .map(VisitLog::getCount)
                .orElse(0L);
    }
}
