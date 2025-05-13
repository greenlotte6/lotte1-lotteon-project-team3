package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.refund.RefundDTO;
import kr.co.lotteOn.dto.refund.RefundPageRequestDTO;
import kr.co.lotteOn.dto.refund.RefundPageResponseDTO;
import kr.co.lotteOn.entity.Refund;
import kr.co.lotteOn.repository.RefundRepository;
import kr.co.lotteOn.service.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminOrderController {
    private final AdminOrderService adminOrderService;
    private final RefundRepository refundRepository;


    /*------------ 관리자 - 주문관리 ------------*/

    //주문관리 - 목록
    @GetMapping("/order/list")
    public String orderList(){
        return "/admin/order/list";
    }

    //주문관리 - 주문현황
    @GetMapping("/order/delivery")
    public String orderDelivery(){
        return "/admin/order/delivery";
    }

    //주문관리 - 교환/환불신청 현황
    @GetMapping("/order/refund")
    public String orderRefund(Model model, RefundPageRequestDTO pageRequestDTO){
        RefundPageResponseDTO pageResponseDTO = adminOrderService.RefundList(pageRequestDTO);

        List<RefundDTO> refundList = pageResponseDTO.getDtoList();
        for(RefundDTO refundDTO : refundList){
            refundDTO.setRefundType(refundDTO.getReturnType());
        }

        model.addAttribute("refund", pageResponseDTO.getDtoList());
        model.addAttribute("page", pageResponseDTO);

        return "/admin/order/refund";
    }

    @PostMapping("/refund/updateStatus")
    public String updateRefundStatus(@RequestParam int refundNo, @RequestParam String status) {
        Refund refund = refundRepository.findById(refundNo).orElseThrow();
        refund.setStatus(status);

        refundRepository.save(refund);
        return "redirect:/admin/order/refund"; // 또는 현재 페이지로 redirect
    }

}
