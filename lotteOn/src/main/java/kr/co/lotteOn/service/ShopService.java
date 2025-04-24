package kr.co.lotteOn.service;

import jakarta.transaction.Transactional;
import kr.co.lotteOn.dto.ShopDTO;
import kr.co.lotteOn.entity.Seller;
import kr.co.lotteOn.entity.Shop;
import kr.co.lotteOn.repository.SellerRepository;
import kr.co.lotteOn.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ShopService {

    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;
    private final SellerRepository sellerRepository;

    //상점 등록
    @Transactional
    public Shop registerShop(ShopDTO shopDTO, String sellerId) {
        log.info("상점 등록 요청 :{}", shopDTO);

        if(shopDTO.getStatus()==null || shopDTO.getStatus().isEmpty()){
            shopDTO.setStatus("운영중");
        }
        if (shopDTO.getManagement()==null || shopDTO.getManagement().isEmpty()){
            shopDTO.setManagement("진행");
        }

        log.info("상점 등록 요청11111111 :{}", shopDTO);

        /*
        //sellerid로 seller 찾기
        Seller seller= sellerRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 판매자가 존재하지 않습니다."));
*/
        log.info("상점 등록 요청111111112222222222 :{}", shopDTO);
        log.info("상점 등록 요청111111113333333333333 :{}", shopDTO);

        Seller seller = modelMapper.map(shopDTO, Seller.class);
        sellerRepository.save(seller);

        //ShopDTO를 Shop 엔티티로 전환
        Shop shop = modelMapper.map(shopDTO, Shop.class);

        shop.setSeller(seller); // Seller와 연결

        // Shop 저장
        System.out.println("저장 전 shop.getId():"+shop.getShopId());
        Shop savedShop = shopRepository.save(shop);
        System.out.println("저장 후 shop.getId():"+savedShop.getShopId());
        log.info("상점 등록 완료:{}", savedShop);
        return savedShop;
    }

    //상점 목록 조회
    public List<ShopDTO> getAllShops() {
        log.info("상점 목록 조회 시작");
        List<Shop> shops = shopRepository.findAll();
        log.info("조회된 상점 목록:{}", shops);
        return shops.stream()
                .map(shop -> modelMapper.map(shop, ShopDTO.class))
                .collect(Collectors.toList());

    }

    //상점 정보 수정

    //상점 정보 삭제




}
