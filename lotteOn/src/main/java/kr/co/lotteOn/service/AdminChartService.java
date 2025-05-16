package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.CategorySalesDTO;
import kr.co.lotteOn.dto.OrderChartDTO;
import kr.co.lotteOn.repository.OrderItemRepository;
import kr.co.lotteOn.repository.OrderRepository;
import kr.co.lotteOn.repository.RefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminChartService {
    private final OrderRepository orderRepository;
    private final RefundRepository refundRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderChartDTO getOrderChartData() {
        List<String> labels = new ArrayList<>();
        List<Integer> orders = new ArrayList<>();
        List<Integer> confirmed = new ArrayList<>();
        List<Integer> refunds = new ArrayList<>();

        for (int i = 4; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            String label = date.format(DateTimeFormatter.ofPattern("MM-dd"));
            labels.add(label);

            int orderCount = orderRepository.countByDate(date);
            int confirmCount = orderRepository.countByDateAndConfirm(date, "구매 확정");
            int refundCount = refundRepository.countByDate(date);

            orders.add(orderCount);
            confirmed.add(confirmCount);
            refunds.add(refundCount);
        }

        return new OrderChartDTO(labels, orders, confirmed, refunds);
    }


    public CategorySalesDTO getCategorySales() {
        List<Long> fashionIds = List.of(25346451L, 25346452L, 25346453L, 25346456L, 25346457L, 25346458L);
        List<Long> lifeIds    = List.of(25346779L, 25346780L, 25346781L);
        List<Long> foodIds    = List.of(25346681L, 25346684L, 25346685L);

        List<Long> allUsed = new ArrayList<>();
        allUsed.addAll(fashionIds);
        allUsed.addAll(lifeIds);
        allUsed.addAll(foodIds);

        int fashionSum = orderRepository.sumActualMoneyByCategoryIds(fashionIds);
        int lifeSum    = orderRepository.sumActualMoneyByCategoryIds(lifeIds);
        int foodSum    = orderRepository.sumActualMoneyByCategoryIds(foodIds);
        int etcSum     = orderRepository.sumActualMoneyExcludeCategoryIds(allUsed);

        return new CategorySalesDTO(
                List.of("패션", "라이프", "식품", "기타"),
                List.of(fashionSum, lifeSum, foodSum, etcSum)
        );
    }

}
