package kr.co.lotteOn.controller;

import kr.co.lotteOn.dto.CategorySalesDTO;
import kr.co.lotteOn.dto.OrderChartDTO;
import kr.co.lotteOn.service.AdminChartService;
import kr.co.lotteOn.service.AdminLotteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/chart")
@RequiredArgsConstructor
public class AdminChartController {

    private final AdminChartService adminChartService;

    @GetMapping("/orders")
    public ResponseEntity<OrderChartDTO> getOrderChart() {
        OrderChartDTO dto = adminChartService.getOrderChartData();
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/sales")
    public ResponseEntity<CategorySalesDTO> getSalesChart() {
        CategorySalesDTO dto = adminChartService.getCategorySales();
        return ResponseEntity.ok(dto);
    }
}
