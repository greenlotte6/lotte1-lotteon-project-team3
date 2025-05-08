package kr.co.lotteOn.service;

import jakarta.transaction.Transactional;
import kr.co.lotteOn.dto.ShopDTO;
import kr.co.lotteOn.entity.Seller;
import kr.co.lotteOn.entity.Shop;
import kr.co.lotteOn.repository.ShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service

public class ShopService {

    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;

    public ShopService(ShopRepository shopRepository, ModelMapper modelMapper) {
        this.shopRepository = shopRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void register(ShopDTO shopDTO, Seller seller) {
        Shop shop = modelMapper.map(shopDTO, Shop.class);

         shop.setSeller(seller);

        shopRepository.save(shop);
    }


}



//    public boolean updateShopStatus(String shopId, String status) {
//        Optional<Shop> optionalShop= shopRepository.findById(Integer.valueOf(shopId));
//        if(optionalShop.isPresent()) {
//            Shop shop = optionalShop.get();
//            shop.setStatus(status);
//            shopRepository.save(shop);
//            return true;
//        }
//        return false;
//    }

