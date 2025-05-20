package kr.co.lotteOn.controller;

import jakarta.validation.Valid;
import kr.co.lotteOn.dto.*;
import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponDTO;
import kr.co.lotteOn.dto.review.ReviewDTO;
import kr.co.lotteOn.entity.*;
import kr.co.lotteOn.repository.OrderRepository;
import kr.co.lotteOn.repository.ReviewRepository;
import kr.co.lotteOn.security.MyUserDetails;
import kr.co.lotteOn.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/product")
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final IssuedCouponService issuedCouponService;
    private final PointService pointService;
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final OrderRepository orderRepository;
    private final ReviewService reviewService;
    private final SearchKeywordService searchKeywordService;

    private void preparePaymentPage(Member member, List<ProductDTO> products, List<Integer> quantities,List <String> options, Model model) {
        List<Map<String, Object>> items = new ArrayList<>();
        int originalTotal = 0;
        int discountAmount = 0;
        int deliveryFeeTotal = 0;

        for (int i = 0; i < products.size(); i++) {
            ProductDTO product = products.get(i);
            int quantity = quantities.get(i);
            String option = options.get(i);

            int productOriginal = product.getPrice() * quantity;
            int productDiscounted = productOriginal * (100 - product.getDiscount()) / 100;
            int productDiscountAmount = productOriginal - productDiscounted;

            // 상품 개별 deliveryFee 누적합 + 최대 3000 제한은 필요에 따라 추가
            int productDeliveryFee = product.getDeliveryFee();
            deliveryFeeTotal += productDeliveryFee;

            // 누적 계산
            originalTotal += productOriginal;
            discountAmount += productDiscountAmount;

            // 리스트에 아이템 추가
            Map<String, Object> item = new HashMap<>();
            item.put("product", product);
            item.put("quantity", quantity);
            item.put("option", option);
            items.add(item);
        }

        // 배송비 최대 3000 제한
        int deliveryFee = Math.min(deliveryFeeTotal, 3000);
        int discountedTotal = originalTotal - discountAmount;
        int finalTotal = discountedTotal + deliveryFee;

        // 쿠폰/포인트
        List<IssuedCouponDTO> coupons = issuedCouponService.getAvailableCouponsForMember(member.getId());
        Point latestPoint = pointService.getLatestPoint(member);
        int memberPoint = (latestPoint != null) ? latestPoint.getTotalPoint() : 0;

        model.addAttribute("items", items);
        model.addAttribute("coupons", coupons);
        model.addAttribute("member", member);

        model.addAttribute("originalTotal", originalTotal);
        model.addAttribute("discountedTotal", discountedTotal);
        model.addAttribute("discountAmount", discountAmount);
        model.addAttribute("deliveryFee", deliveryFee);
        model.addAttribute("finalTotal", finalTotal);
        model.addAttribute("memberPoint", memberPoint);

        Set<String> companyNames = products.stream()
                .map(ProductDTO::getCompanyName)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        String senderNames = String.join(", ", companyNames);
        model.addAttribute("senderNames", senderNames);
    }

    //상품 - 목록
    @GetMapping("/list")
    public String productList(@RequestParam(name = "categoryId", required = false) Long categoryId,
                              @RequestParam(name = "filter", required = false) String filter,
                              @RequestParam(name = "sort", required = false, defaultValue = "date") String sort,
                              @RequestParam(name = "event", required = false) String event,
                              Model model) {
        List<ProductDTO> products;
        List<ProductDTO> bestProducts;

        if ("special".equals(event)) {
            products = productService.getDiscountedProductsWithReview();
            bestProducts = productService.getBest10DiscountedProducts();
        }
        else if ("fashion".equals(filter)) {
            List<Long> categoryIds = List.of(25346451L, 25346452L,25346453L,25346456L,25346457L,25346458L);
            products = productService.getSortedProductsByCategories(categoryIds, sort);
            bestProducts = productService.getBest10ProductsByCategories(categoryIds);
        }
        else if ("food".equals(filter)) {
            List<Long> categoryIds = List.of(25346681L, 25346684L, 25346685L);
            products = productService.getSortedProductsByCategories(categoryIds, sort);
            bestProducts = productService.getBest10ProductsByCategories(categoryIds);
        }
        else if ("life".equals(filter)) {
            List<Long> categoryIds = List.of(25346779L, 25346780L, 25346781L);
            products = productService.getSortedProductsByCategories(categoryIds, sort);
            bestProducts = productService.getBest10ProductsByCategories(categoryIds);
        }
        else if (categoryId != null) {
            products = productService.getSortedProductsByCategory(categoryId, sort);
            bestProducts = productService.getBest10ProductsByCategoryId(categoryId);
        }else {
            products = productService.getSortedAllProducts(sort);
            bestProducts = productService.getBest10Products();
        }

        List<ProductDTO> limitedProducts = products.stream()
                .limit(10)
                .toList();

        model.addAttribute("products", limitedProducts);
        model.addAttribute("bestProducts", bestProducts);
        model.addAttribute("sort", sort);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("event", event);
        model.addAttribute("filter", filter);
        return "/product/list";
    }

    //상품 - 상세보기
    @GetMapping("/detail")
    public String detail(@RequestParam String productCode,
                         @RequestParam(defaultValue = "0") int page,
                         Model model) {
        ProductDTO product = productService.getProductByCode(productCode);
        if (product == null) {
            return "redirect:/product/list";
        }

        Pageable pageable = PageRequest.of(page, 5);
        Page<ReviewDTO> reviews = reviewService.getReviewsByProductPaged(productCode, pageable);

        // 페이지 그룹 계산
        int currentPage = reviews.getNumber(); // 0-based
        int totalPages = reviews.getTotalPages();
        int groupSize = 10;

        int startPage = (currentPage / groupSize) * groupSize;
        int endPage = Math.min(startPage + groupSize, totalPages);

        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "/product/detail";
    }

    @GetMapping("/payment")
    public String paymentGet(@AuthenticationPrincipal MyUserDetails myUserDetails,
                             @RequestParam List<String> productCode,
                             @RequestParam List<Integer> quantity,
                             @RequestParam List<String> option,
                             @RequestParam (required = false) List<Long> cartIds,
                             Model model) {

        Member member = myUserDetails.getMember();

        // productCode에 따라 ProductDTO 리스트 구성
        List<ProductDTO> products = new ArrayList<>();
        for (String code : productCode) {
            ProductDTO product = productService.getProductByCode(code);
            if (product == null) {
                log.warn("productCode [{}] 에 해당하는 상품이 존재하지 않음", code);
            } else {
                products.add(product);
            }
        }

        if (products.size() != quantity.size()) {
            log.error("상품 수와 수량 수가 다름. 필터링 중 일부 상품이 null로 제거됨");
            return "redirect:/product/cart"; // 또는 오류 페이지
        }

        preparePaymentPage(member, products, quantity, option, model);
        model.addAttribute("cartIds", cartIds);
        return "/product/payment";
    }

    @PostMapping("/payment")
    public String paymentPost(@AuthenticationPrincipal MyUserDetails myUserDetails,
                              @RequestParam List<String> productCode,
                              @RequestParam List<Integer> quantity,
                              @RequestParam List<String> option,
                              Model model) {

        if (myUserDetails == null) {
            return "redirect:/member/login"; // 단일상품 redirect는 생략 가능
        }

        Member member = myUserDetails.getMember();
        List<ProductDTO> products = new ArrayList<>();
        for (String code : productCode) {
            ProductDTO product = productService.getProductByCode(code);
            if (product == null) {
                log.warn("productCode [{}] 에 해당하는 상품이 존재하지 않음", code);
            } else {
                products.add(product);
            }
        }

        if (products.size() != quantity.size()) {
            log.error("상품 수와 수량 수가 다름. 필터링 중 일부 상품이 null로 제거됨");
            return "redirect:/product/cart"; // 또는 오류 페이지
        }

        preparePaymentPage(member, products, quantity, option, model);
        return "/product/payment";
    }
    @GetMapping("/payment/coupons")
    @ResponseBody
    public List<IssuedCouponDTO> getCoupons(@AuthenticationPrincipal MyUserDetails myUserDetails) {
        String memberId = myUserDetails.getMember().getId();
        return issuedCouponService.getAvailableCouponsForMember(memberId);
    }

    //상품 - 주문완료
    @GetMapping("/completeOrder/{orderCode}")
    public String showCompleteOrder(@PathVariable String orderCode, Model model) {

        Order order = orderRepository.findFullOrderByCode(orderCode)
                .orElseThrow(() -> new IllegalArgumentException("주문 없음"));

        List<OrderItem> items = order.getItems();
        OrderItem firstItem = items.get(0);
        OrderResultDTO orderResult = OrderResultDTO.fromEntity(order, firstItem);

        int totalPrice = items.stream().mapToInt(OrderItem::getTotal).sum();
        int originalPrice = items.stream()
                        .mapToInt(i -> i.getPrice() * i.getQuantity())
                                .sum();
        Long actualMoney = order.getActualMoney();
        int totalDiscount = Integer.parseInt(order.getDiscount());
        int productDiscount = items.stream()
                        .mapToInt(i -> (i.getPrice() * i.getDiscount() / 100) * i.getQuantity())
                                .sum();
        int couponPointDiscount = totalDiscount - productDiscount;

        model.addAttribute("orderResult", orderResult);
        model.addAttribute("items", items);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("originalPrice", originalPrice);
        model.addAttribute("actualMoney", actualMoney);
        model.addAttribute("productDiscount", productDiscount);
        model.addAttribute("couponPointDiscount", couponPointDiscount);
        return "/product/completeOrder";
    }

    @PostMapping("/completeOrder")
    public String completeOrder(@ModelAttribute OrderRequestDTO orderRequestDTO,
                                @ModelAttribute OrderItemListDTO itemListDTO,
                                @RequestParam(value = "cartIds", required = false) List<Long> cartIds,
                                RedirectAttributes redirectAttributes) {

        orderRequestDTO.setCartIds(cartIds);
        log.info("넘어온 cartIds = {}", orderRequestDTO.getCartIds()); // 👈

        String orderCode = orderService.createOrder(orderRequestDTO, itemListDTO.getItems());

        pointService.usePoint(orderRequestDTO.getMemberId(), orderRequestDTO.getUsedPoint(), orderCode);

        if (orderRequestDTO.getIssuedNo() > 0) {
            issuedCouponService.markCouponAsUsed(orderRequestDTO.getIssuedNo(), orderCode);
        }

        return "redirect:/product/completeOrder/" + orderCode;

    }

    @GetMapping("/searchResult")
    public String searchResult(@RequestParam String keyword, Model model) {
        // 검색어 저장 (이건 그대로 유지)
        searchKeywordService.saveOrUpdateKeyword(keyword);

        // Redis 캐싱이 적용된 DTO 리스트 호출
        List<ProductDTO> searchResults = productService.searchingProducts(keyword, keyword);

        // View에 전달
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchResults", searchResults);

        return "/product/searchResult";
    }

    @GetMapping("/api/search/auto")
    @ResponseBody
    public List<String> autoComplete(@RequestParam String keyword) {
        return productService.autoCompleteProductNames(keyword);
    }


}
