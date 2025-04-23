package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.ShopDTO;
import kr.co.lotteOn.entity.Seller;
import kr.co.lotteOn.entity.Shop;
import kr.co.lotteOn.repository.SellerRepository;
import kr.co.lotteOn.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;

    //아이디 존재 여부 확인
    public boolean existsByShopId(int shopId) {
        return shopRepository.existsByShopId(shopId);
    }

    //상점 등록
    public void registerShop(ShopDTO shopDTO) {
        Shop shop = modelMapper.map(shopDTO, Shop.class);

        shopRepository.save(shop);
    }


}
