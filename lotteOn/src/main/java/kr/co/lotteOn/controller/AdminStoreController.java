package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.ShopDTO;
import kr.co.lotteOn.entity.Shop;
import kr.co.lotteOn.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminStoreController {
    private final ShopService shopService;


    /*------------ 관리자 - 상점관리 ------------*/

    //상점관리 - 상점목록
    @GetMapping("/shop/list")
    public String shopList(Model model) {
        model.addAttribute("shops", shopService.getAllShops());
        return "/admin/shop/list";
    }

    //상점 등록 폼
    @GetMapping("/shop/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("shopDTO", new ShopDTO());
        return "/admin/shop/register";
    }

   //상점 등록 처리
    @PostMapping("/shop/register")
    public String registerShop(@ModelAttribute ShopDTO shopDTO, Authentication authentication ) {
        try{
            String sellerId= authentication.getName();

            shopService.registerShop(shopDTO,sellerId);
            //shopDTO.setSellerId(sellerId);
            System.out.println("넘어온 shop id: " + shopDTO.getShopId());
            //Shop registerShop = shopService.registerShop(shopDTO);
            return "redirect:/admin/shop/list";
        }catch (IllegalArgumentException e){
            return "/admin/shop/list";
        }
    }




    //상점관리 - 매출관리
    @GetMapping("/shop/sales")
    public String shopSales(){
        return "/admin/shop/sales";
    }


}