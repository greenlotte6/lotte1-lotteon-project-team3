package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.TermsDTO;
import kr.co.lotteOn.entity.Terms;
import kr.co.lotteOn.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TermsService {
    private final TermsRepository termsRepository;
    private final ModelMapper modelMapper;

    public TermsDTO findTerms() {
        Optional<Terms> optTerms = termsRepository.findById(1);

        if (optTerms.isPresent()) {
            TermsDTO termsDTO = modelMapper.map(optTerms.get(), TermsDTO.class);
            return termsDTO;
        }
        return null;
    }

    public void modifyTerms(TermsDTO termsDTO) {
        int termsId = 1;
        Terms terms = termsRepository.findById(termsId).get();

        terms.setBuyer(termsDTO.getBuyer());
        terms.setPlace(termsDTO.getPlace());
        terms.setTrade(termsDTO.getTrade());
        terms.setSeller(termsDTO.getSeller());
        terms.setPrivacy(termsDTO.getPrivacy());

        termsRepository.save(terms);
    }

    public String findTermsContentByType(String type) {
        Terms terms = termsRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("약관 정보 없음"));

        switch (type) {
            case "buyer": return terms.getBuyer();
            case "seller": return terms.getSeller();
            case "trade": return terms.getTrade();
            case "place": return terms.getPlace();
            case "privacy": return terms.getPrivacy();
            default: throw new IllegalArgumentException("유효하지 않은 약관 타입: " + type);
        }
    }

}