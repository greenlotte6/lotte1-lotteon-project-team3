package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.TermsDTO;
import kr.co.lotteOn.entity.Terms;
import kr.co.lotteOn.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

}