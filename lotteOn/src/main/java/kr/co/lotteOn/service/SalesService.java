package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.SalesDTO;
import kr.co.lotteOn.entity.Sales;
import kr.co.lotteOn.entity.Seller;
import kr.co.lotteOn.repository.OrderItemRepository;
import kr.co.lotteOn.repository.OrderRepository;
import kr.co.lotteOn.repository.SalesRepository;
import kr.co.lotteOn.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalesService {

    private final SalesRepository salesRepository;
    private final SellerRepository sellerRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    //주문건수 전
//    public List<SalesDTO> getSalesList() {
//        List<SalesDTO> salesList = sellerRepository.findAllSellerSales();
//        log.info("salesList: {}", salesList);
//        return sellerRepository.findAllSellerSales();
//    }

    //최신
//    public List<SalesDTO> getSalesList(){
//        List<SalesDTO> salesList= sellerRepository.findAllSellerSales();
//
//        for (SalesDTO dto : salesList){
//            Integer count = orderItemRepository.sumQuantityByCompanyName(dto.getCompanyName());
//            dto.setOrderCount(count != null ? count : 0);
//        }
//
//        return salesList;
//    }
    public List<SalesDTO> getSalesList(){
        List<SalesDTO> salesList = sellerRepository.findAllSellerSales();

        for (SalesDTO dto : salesList) {

            //추가
            String companyName = dto.getCompanyName();

            //결제완료 건수 조회
            Integer completedOrderCount = orderRepository.countCompletedOrdersByCompanyName(dto.getCompanyName());
            dto.setPayDone(completedOrderCount != null ? completedOrderCount : 0);

            //주문건수 조회
            Integer count = orderItemRepository.sumQuantityByCompanyName(dto.getCompanyName());
            dto.setOrderCount(count != null ? count : 0);

            //총 주문 금액
            Integer totalPrice = orderItemRepository.sumOrderPriceByCompanyName(dto.getCompanyName());
            dto.setOrderTotal(totalPrice != null ? totalPrice : 0);

            //할인 포함 총 매출 금액
            Integer salesTotal= orderItemRepository.sumsalesTotalByCompanyName(dto.getCompanyName());
            dto.setSalesTotal(salesTotal != null ? salesTotal : 0);



        }
        return salesList;
    }










}

