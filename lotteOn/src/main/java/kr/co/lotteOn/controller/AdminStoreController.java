package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.SellerDTO;
import kr.co.lotteOn.dto.ShopDTO;
import kr.co.lotteOn.repository.SellerProjection;
import kr.co.lotteOn.service.SellerService;
import kr.co.lotteOn.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminStoreController {
    private final ShopService shopService;
    private final ModelMapper modelMapper;
    private final SellerService sellerService;


    /*------------ 관리자 - 상점관리 ------------*/

    //상점관리 - 상점목록
    @GetMapping("/shop/list")
    public String shopList(Model model) {

        List<SellerProjection> sellerProjectionList = sellerService.getSellerList();

        model.addAttribute("sellers", sellerProjectionList);


        return "/admin/shop/list";
    }


    // 상점 등록 처리
    @PostMapping("/shop/list")
    public String registerShopPost(@ModelAttribute SellerDTO sellerDTO){
        log.info("registerShopPost 호출됨. SellerDTO: {}", sellerDTO);
        if (sellerDTO == null) {
            log.error("sellerDTO 객체가 null입니다!");
        }else{
            log.info("sellerDTO 값 확인: {}", sellerDTO);
        }
        sellerService.register(sellerDTO);

        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setSellerId(sellerDTO.getSellerId());
        shopDTO.setShopName(sellerDTO.getCompanyName());
        shopDTO.setPassword(sellerDTO.getPassword());
        shopDTO.setCompanyName(sellerDTO.getCompanyName());
        shopDTO.setDelegate(sellerDTO.getDelegate());
        shopDTO.setBusinessNo(sellerDTO.getBusinessNo());
        shopDTO.setCommunicationNo(sellerDTO.getCommunicationNo());
        shopDTO.setHp(sellerDTO.getHp());
        shopDTO.setFax(sellerDTO.getFax());
        shopDTO.setZip(sellerDTO.getZip());
        shopDTO.setAddr1(sellerDTO.getAddr1());
        shopDTO.setAddr2(sellerDTO.getAddr2());

        shopService.register(shopDTO);

        log.info("상점 등록이 완료 되었습니다.");
        return "redirect:/admin/shop/list";
    }



    //상점관리 - 매출관리
    @GetMapping("/shop/sales")
    public String shopSales(){
        return "/admin/shop/sales";
    }


}