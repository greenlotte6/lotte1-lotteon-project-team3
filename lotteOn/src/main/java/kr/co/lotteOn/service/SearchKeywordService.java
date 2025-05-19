package kr.co.lotteOn.service;

import jakarta.transaction.Transactional;
import kr.co.lotteOn.entity.SearchKeyword;
import kr.co.lotteOn.repository.SearchKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchKeywordService {

    private final SearchKeywordRepository searchKeywordRepository;

    @Transactional
    public void saveOrUpdateKeyword(String keyword) {
        SearchKeyword searchKeyword = searchKeywordRepository.findByKeyword(keyword)
                .map(sk -> {
                    sk.setCount(sk.getCount() + 1);
                    return sk;
                })
                .orElse(SearchKeyword.builder()
                        .keyword(keyword)
                        .count(1)
                        .build());

        searchKeywordRepository.save(searchKeyword);
    }

    public List<SearchKeyword> getTopKeywords() {
        return searchKeywordRepository.findTopKeywords();
    }

    @Transactional
    public void resetAllKeywordCounts() {
        searchKeywordRepository.resetAllCounts();
    }
}