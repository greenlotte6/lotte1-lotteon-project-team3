package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.SalesDTO;
import kr.co.lotteOn.entity.Sales;
import kr.co.lotteOn.entity.Seller;
import kr.co.lotteOn.repository.OrderItemRepository;
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
    private final OrderItemRepository orderItemRepository;

    //주문건수 전
    public List<SalesDTO> getSalesList() {
        List<SalesDTO> salesList = sellerRepository.findAllSellerSales();
        log.info("salesList: {}", salesList);
        return sellerRepository.findAllSellerSales();
    }




    }

