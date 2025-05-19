package kr.co.lotteOn.scheduler;

import kr.co.lotteOn.repository.SearchKeywordRepository;
import kr.co.lotteOn.service.SearchKeywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchKeywordScheduler {

    private final SearchKeywordService searchKeywordService;

    // 매주 월요일 00:00:00
    @Scheduled(cron = "0 0 0 * * MON")
    public void resetSearchKeywordCount() {
        log.info("검색어 카운트 초기화 시작");

        searchKeywordService.resetAllKeywordCounts();

        log.info("검색어 카운트 초기화 완료");
    }
}