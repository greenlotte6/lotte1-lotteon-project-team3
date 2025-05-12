package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.SalesDTO;
import kr.co.lotteOn.repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalesService {

    private final SalesRepository salesRepository;

    public List<SalesDTO> getSalesList(){
            return salesRepository.findAllSales();
        }

    }

