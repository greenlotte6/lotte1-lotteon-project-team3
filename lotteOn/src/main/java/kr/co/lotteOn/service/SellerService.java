package kr.co.lotteOn.service;


import kr.co.lotteOn.dto.SellerDTO;
import kr.co.lotteOn.dto.ShopDTO;
import kr.co.lotteOn.entity.Seller;
import kr.co.lotteOn.entity.Shop;
import kr.co.lotteOn.repository.SellerRepository;
import kr.co.lotteOn.repository.SellerProjection;
import kr.co.lotteOn.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.lotteOn.entity.QSeller.seller;

@Slf4j
@RequiredArgsConstructor
@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final ShopRepository shopRepository;

    //아이디 존재 여부 확인
    public boolean existsBySellerId(String sellerId) {
        return sellerRepository.existsBySellerId(sellerId);
    }

    public boolean isCompanyNameExist(String companyName) {
        return sellerRepository.existsByCompanyName(companyName);
    }

    public boolean isBusinessNoExist(String businessNo) {
        return sellerRepository.existsByBusinessNo(businessNo);
    }

    public boolean isCommunicationNoExist(String communicationNo) {
        return sellerRepository.existsByCommunicationNo(communicationNo);
    }

    public boolean isSHpExist(String hp) {
        return sellerRepository.existsByHp(hp);
    }

    public boolean isFaxExist(String fax) {
        return sellerRepository.existsByFax(fax);
    }


    public Seller register(SellerDTO sellerDTO) {
        //비밀번호 암호화
        String encodedPass= passwordEncoder.encode(sellerDTO.getPassword());
        sellerDTO.setPassword(encodedPass);

        //엔티티 변환
        Seller seller = modelMapper.map(sellerDTO, Seller.class);

        //저장
        return sellerRepository.save(seller);
    }


    public List<SellerProjection> getSellerList(){
        return sellerRepository.findAllBy();
    }


    //삭제
    @Transactional
    public void deleteShops(List<String> sellerIds) {
        //추가
        shopRepository.deleteBySellerIds(sellerIds);

        List<SellerProjection> sellers= sellerRepository.findBySellerIdIn(sellerIds);

        for(SellerProjection seller: sellers){
            String sellerId = seller.getSellerId();
            try {
                sellerRepository.deleteById(sellerId);
                log.info("Seller with id {} deleted successfully. " + sellerId);
            }catch (Exception e){
                log.error("Failed to delete seller with id {}: {} ",sellerId, e.getMessage());
            }
        }

    }

//    public Page<SellerProjection> getSellerListPaged(Pageable pageable) {
//        return sellerRepository.findAllBy(pageable);
//    }

    //조회
    public Page<SellerProjection> getSellerListPaged(Pageable pageable ,String type, String keyword) {
        if (type != null && keyword != null && !type.isEmpty() && !keyword.isEmpty()) {
            return sellerRepository.findBySearch(type, keyword, pageable);
        }else{
            return sellerRepository.findAllProjections(pageable);
        }
    }


//    public void modifyState(String sellerId) {
//
//        Seller seller = Seller.builder()
//                .sellerId(sellerId)
//                .build();
//
//        List<Shop> shopList = shopRepository.findBySeller(seller);
//
//        for(Shop shop: shopList){
//            if(shop.getStatus().equals("운영중")){
//                shop.setStatus("운영중지");
//            }else if(shop.getStatus().equals("운영중지")){
//                shop.setStatus("운영준비");
//            }else{
//                shop.setStatus("운영중");
//            }
//
//            shopRepository.save(shop);
//        }
//
//
//    }

    public void modifyState(String sellerId){
        Seller seller = sellerRepository.findBySellerId(sellerId);
        List<Shop> shopList = shopRepository.findBySeller(seller);

        String nextStatus= "운영중";

        String currentStatus = seller.getStatus();
        switch (currentStatus) {
                case "운영중":
                    nextStatus = "운영중지";
                    break;
                case "운영중지":
                    nextStatus= "운영준비";
                    break;
            case "운영준비":
                    nextStatus= "운영중";
                    break;
            }
           for (Shop shop : shopList) {
               shop.setStatus(nextStatus);
               shopRepository.save(shop);
           }

        seller.setStatus(nextStatus);
        sellerRepository.save(seller);
    }
}
