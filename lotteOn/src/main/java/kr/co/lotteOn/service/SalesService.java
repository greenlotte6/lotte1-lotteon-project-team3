package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.SalesDTO;
import kr.co.lotteOn.entity.Sales;
import kr.co.lotteOn.repository.SalesRepository;
import kr.co.lotteOn.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalesService {

    private final SalesRepository salesRepository;
    private final SellerRepository sellerRepository;

    public List<SalesDTO> getSalesList(){
        return sellerRepository.findAllSellerSales();
    }

    public List<SalesDTO> getOrderCountsBySeller(){
        List<Object[]> results= salesRepository.findOrderCountsBySeller();
        List<SalesDTO> salesDTOs= new ArrayList<>();

        for(Object[] result: results){
            String companyName= (String) result[0];
            int orderCount= ((Number) result[1]).intValue();
            SalesDTO salesDTO= new SalesDTO(companyName, orderCount);
            salesDTO.add(salesDTO);
        }
        return salesDTOs;
    }

    }

