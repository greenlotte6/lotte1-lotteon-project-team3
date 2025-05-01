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
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void register(ShopDTO shopDTO) {
        Shop shop = modelMapper.map(shopDTO, Shop.class);

        shopRepository.save(shop);
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
}
