package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.delivery.DeliveryDTO;
import kr.co.lotteOn.dto.delivery.DeliveryPageRequestDTO;
import kr.co.lotteOn.dto.delivery.DeliveryPageResponseDTO;
import kr.co.lotteOn.dto.order.OrderDTO;
import kr.co.lotteOn.dto.order.OrderPageRequestDTO;
import kr.co.lotteOn.dto.order.OrderPageResponseDTO;
import kr.co.lotteOn.dto.refund.RefundDTO;
import kr.co.lotteOn.dto.refund.RefundPageRequestDTO;
import kr.co.lotteOn.dto.refund.RefundPageResponseDTO;
import kr.co.lotteOn.entity.Order;
import kr.co.lotteOn.entity.Refund;
import kr.co.lotteOn.repository.OrderRepository;
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
    private final OrderRepository orderRepository;


    /*------------ 관리자 - 주문관리 ------------*/

    //주문관리 - 목록
    @GetMapping("/order/list")
    public String orderList(Model model, OrderPageRequestDTO pageRequestDTO) {
        OrderPageResponseDTO pageResponseDTO = adminOrderService.OrderList(pageRequestDTO);

        for(OrderDTO orderDTO : pageResponseDTO.getDtoList()){
            orderDTO.setPayment(orderDTO.getPaymentName());
        }

        model.addAttribute("page", pageResponseDTO);
        model.addAttribute("order", pageResponseDTO.getDtoList());

        return "/admin/order/list";
    }

    //주문관리 - 배송 정보 등록
    @PostMapping("/order/post")
    public String registerPost(DeliveryDTO deliveryDTO) {

        int no = adminOrderService.deliveryWrite(deliveryDTO);


        return "redirect:/admin/order/list";
    }

    //주문관리 - 주문현황
    @GetMapping("/order/delivery")
    public String orderDelivery(Model model, DeliveryPageRequestDTO pageRequestDTO){
        DeliveryPageResponseDTO pageResponseDTO = adminOrderService.DeliveryList(pageRequestDTO);

        for(DeliveryDTO deliveryDTO : pageResponseDTO.getDtoList()){
            deliveryDTO.setPayment(deliveryDTO.getPaymentName());
        }

        model.addAttribute("page", pageResponseDTO);
        model.addAttribute("delivery", pageResponseDTO.getDtoList());


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
        Order order = orderRepository.findByOrderCode(refund.getOrderCode());

        refund.setStatus(status);

        String statusMessage = refund.getStatus();
        String currentStatus = order.getRefundStatus();


        if(currentStatus.contains("반품")){
            order.setRefundStatus("반품" + statusMessage);
            order.setConfirm("반품" + statusMessage);
        } else if(currentStatus.contains("교환")){
            order.setRefundStatus("교환" + statusMessage);
            order.setConfirm("교환" + statusMessage);
        }

        orderRepository.save(order);

        refundRepository.save(refund);
        return "redirect:/admin/order/refund"; // 또는 현재 페이지로 redirect
    }

}
