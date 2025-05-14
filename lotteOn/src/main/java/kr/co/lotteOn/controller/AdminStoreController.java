package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.SalesDTO;
import kr.co.lotteOn.dto.SellerDTO;
import kr.co.lotteOn.dto.ShopDTO;
import kr.co.lotteOn.entity.Sales;
import kr.co.lotteOn.entity.Seller;
import kr.co.lotteOn.repository.SalesRepository;
import kr.co.lotteOn.repository.SellerProjection;
import kr.co.lotteOn.repository.ShopRepository;
import kr.co.lotteOn.service.OrderItemService;
import kr.co.lotteOn.service.SalesService;
import kr.co.lotteOn.service.SellerService;
import kr.co.lotteOn.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminStoreController {
    private final ShopService shopService;
    private final ModelMapper modelMapper;
    private final SellerService sellerService;
    private final ShopRepository shopRepository;
    private final SalesService salesService;
    private final SalesRepository salesRepository;
    private final OrderItemService orderItemService;


    /*------------ 관리자 - 상점관리 ------------*/

    //상점관리 - 상점목록
    @GetMapping("/shop/list")
    public String shopList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model){

        int pageSize= 10;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("regDate").descending());

        Page<SellerProjection> sellerPage= sellerService.getSellerListPaged(pageable, type, keyword);

        model.addAttribute("sellers", sellerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", sellerPage.getTotalPages());
        model.addAttribute("type", type);
        model.addAttribute("keyword", keyword);

        return "/admin/shop/list";

        }


//    public String shopList(Model model) {
//
//        List<SellerProjection> sellerProjectionList = sellerService.getSellerList();
//
//        model.addAttribute("sellers", sellerProjectionList);
//
//
//        return "/admin/shop/list";



    // 상점 등록 처리
    @PostMapping("/shop/list")
    public String registerShopPost(@ModelAttribute SellerDTO sellerDTO){
        log.info("registerShopPost 호출됨. SellerDTO: {}", sellerDTO);
        if (sellerDTO == null) {
            log.error("sellerDTO 객체가 null입니다!");
        }else{
            log.info("sellerDTO 값 확인: {}", sellerDTO);
        }

        Seller seller = sellerService.register(sellerDTO);

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

        shopService.register(shopDTO, seller);

        log.info("상점 등록이 완료 되었습니다.");
        return "redirect:/admin/shop/list";
    }



    //상점 삭제 처리
    @PostMapping("/shop/delete")
    public String deleteShops(@RequestParam("sellerIds") String sellerIds){
        String[] ids= sellerIds.split(",");
        List<String> sellerIdList= Arrays.asList(ids);

        try{
            sellerService.deleteShops(sellerIdList);
        }catch (Exception e){
            log.error("Failed to delete some sellers: {}", e.getMessage());
            return "redirect:/admin/shop/list?error=true";
        }
        log.info("Selected shops have been deleted successfully.");
        return "redirect:/admin/shop/list";
    }


    // 상점 상태 변화
    @GetMapping("/shop/state")
    public String shopState(@RequestParam("sellerId") String sellerId,
                            @RequestParam("page") int page,
                            @RequestParam(value = "type", required = false) String type,
                            @RequestParam(value = "keyword", required = false) String keyword,
                            Model model){
        sellerService.modifyState(sellerId);

        String redirectUrl= String.format("redirect:/admin/shop/list?page=%d", page);
        if (type != null && !type.isEmpty()) {
            redirectUrl += "&type=" + type;
        }
        if (keyword != null && !keyword.isEmpty()) {
            redirectUrl += "&keyword=" + keyword;
        }

        return redirectUrl;
    }






//    @PostMapping("/shop/update-shop-status")
//    @ResponseBody
//    public Map<String, Object> updateShopStatus(@RequestParam String sellerId, @RequestParam String status){
//        Map<String, Object> response = new HashMap<>();
//
//        Shop shop = shopRepository.findBySellerId(sellerId);
//        if (shop == null) {
//            shop.setStatus(status);
//            shopRepository.save(shop);
//            response.put("success", true);
//        }else{
//            response.put("success", false);
//        }
//        return response;
//    }









    //상점관리 - 매출관리
    @GetMapping("/shop/sales")
    public String shopSales(Model model){

        List<SalesDTO> salesList= salesService.getSalesList();

        model.addAttribute("salesList", salesList);
        return "/admin/shop/sales";
    }





}