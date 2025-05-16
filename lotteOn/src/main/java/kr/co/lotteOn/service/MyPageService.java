package kr.co.lotteOn.service;

import com.querydsl.core.Tuple;
import jakarta.transaction.Transactional;
import kr.co.lotteOn.dto.*;
import kr.co.lotteOn.dto.refund.RefundDTO;
import kr.co.lotteOn.dto.refund.RefundPageRequestDTO;
import kr.co.lotteOn.dto.refund.RefundPageResponseDTO;
import kr.co.lotteOn.dto.review.ReviewDTO;
import kr.co.lotteOn.dto.order.OrderDTO;
import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponDTO;
import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponPageRequestDTO;
import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponPageResponseDTO;
import kr.co.lotteOn.dto.order.OrderPageRequestDTO;
import kr.co.lotteOn.dto.order.OrderPageResponseDTO;
import kr.co.lotteOn.dto.point.PointDTO;
import kr.co.lotteOn.dto.point.PointPageRequestDTO;
import kr.co.lotteOn.dto.point.PointPageResponseDTO;
import kr.co.lotteOn.dto.qna.QnaDTO;
import kr.co.lotteOn.dto.qna.QnaPageRequestDTO;
import kr.co.lotteOn.dto.qna.QnaPageResponseDTO;
import kr.co.lotteOn.dto.review.ReviewPageRequestDTO;
import kr.co.lotteOn.dto.review.ReviewPageResponseDTO;
import kr.co.lotteOn.entity.*;
import kr.co.lotteOn.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyPageService {

    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final IssuedCouponRepository issuedCouponRepository;
    private final QnaRepository qnaRepository;
    private final CouponRepository couponRepository;
    private final PointRepository pointRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final RefundRepository refundRepository;
    private final PointService pointService;
    private final ReviewService reviewService;

    //회원별 문의내역
    public QnaPageResponseDTO getQnaByWriter(QnaPageRequestDTO qnaPageRequestDTO) {
        Pageable pageable = qnaPageRequestDTO.getPageable("noticeNo");

        Page<Tuple> pageQna = qnaRepository.searchAllByWriter(qnaPageRequestDTO, pageable);
        log.info("pageQna: {}", pageQna);

        List<QnaDTO> qnaList = pageQna
                .getContent()
                .stream()
                .map(tuple -> {
                    Qna qna = tuple.get(0, Qna.class);
                    String writer = tuple.get(1, String.class);

                    QnaDTO qnaDTO = modelMapper.map(qna, QnaDTO.class);
                    qnaDTO.setWriter(writer);

                    return qnaDTO;
                }).toList();

        log.info("qnaList: {}", qnaList);

        int total = (int) pageQna.getTotalElements();

        return QnaPageResponseDTO
                .builder()
                .pageRequestDTO(qnaPageRequestDTO)
                .dtoList(qnaList)
                .total(total)
                .build();
    }

    //회원별 문의내역 - 글보기
    public QnaDTO findById(int qnaNo){
        Optional<Qna> optQna = qnaRepository.findById(qnaNo);
        if (optQna.isPresent()) {
            Qna qna = optQna.get();

            QnaDTO qnaDTO = modelMapper.map(qna, QnaDTO.class);

            return qnaDTO;
        }

        return null;
    }

    //회원별 쿠폰내역
    public IssuedCouponPageResponseDTO getCouponByWriter(IssuedCouponPageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("issuedNo");

        Page<Tuple> pageCoupon = issuedCouponRepository.searchAllByMemberId(pageRequestDTO, pageable);

        List<IssuedCouponDTO> couponList = pageCoupon
                .getContent()
                .stream()
                .map(tuple -> {
                    IssuedCoupon issuedCoupon = tuple.get(0, IssuedCoupon.class);
                    Coupon coupon = tuple.get(1, Coupon.class);
                    String memberId = tuple.get(2, String.class);

                    IssuedCouponDTO issuedCouponDTO = modelMapper.map(issuedCoupon, IssuedCouponDTO.class);

                    issuedCouponDTO.setCouponCode(coupon.getCouponCode());
                    issuedCouponDTO.setCouponType(coupon.getCouponType());
                    issuedCouponDTO.setCouponName(coupon.getCouponName());
                    issuedCouponDTO.setBenefit(coupon.getBenefit());
                    issuedCouponDTO.setCompanyName(coupon.getCompanyName());
                    issuedCouponDTO.setStartDate(coupon.getStartDate());
                    issuedCouponDTO.setEndDate(coupon.getEndDate());
                    issuedCouponDTO.setEtc(coupon.getEtc());

                    issuedCouponDTO.setMemberId(memberId);

                    return issuedCouponDTO;

                }).collect(Collectors.toList());

        int total = (int) pageCoupon.getTotalElements();

        return IssuedCouponPageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(couponList)
                .total(total)
                .build();
    }

    //회원별 쿠폰내역 - 쿠폰 자세히보기
    public IssuedCouponDTO findCouponById(int issuedNo) {
        Optional<IssuedCoupon> optIssuedCoupon = issuedCouponRepository.findById(issuedNo);
        if (optIssuedCoupon.isPresent()) {
            IssuedCoupon coupon = optIssuedCoupon.get();

            // issuedCoupon에서 couponCode 꺼내기
            String couponCode = coupon.getCoupon().getCouponCode();

            // couponCode로 Coupon 엔티티 조회
            Optional<Coupon> optCoupon = couponRepository.findByCouponCode(couponCode);
            if (optCoupon.isPresent()) {
                Coupon coupon1 = optCoupon.get();

                // IssuedCoupon -> IssuedCouponDTO 변환
                IssuedCouponDTO dto = modelMapper.map(coupon, IssuedCouponDTO.class);

                dto.setCouponCode(coupon.getCoupon().getCouponCode());
                dto.setCouponType(coupon.getCoupon().getCouponType());
                dto.setCouponName(coupon.getCoupon().getCouponName());
                dto.setBenefit(coupon.getCoupon().getBenefit());
                dto.setStartDate(coupon.getCoupon().getStartDate());
                dto.setEndDate(coupon.getCoupon().getEndDate());
                dto.setEtc(coupon.getCoupon().getEtc());
                dto.setCompanyName(coupon.getCoupon().getCompanyName());

                return dto;
            }

        }
        return null;
    }


    //회원별 포인트 적립내역 출력
    public PointPageResponseDTO getPointByMemberId(PointPageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("memberId");
        Page<Tuple> pagePoint = pointRepository.findAllByMemberId(pageRequestDTO, pageable);

        List<PointDTO> pointDTOList = pagePoint
                .getContent()
                .stream()
                .map(tuple -> {
                    Point point = tuple.get(0, Point.class);
                    String memberId = tuple.get(1, String.class);

                    PointDTO pointDTO = modelMapper.map(point, PointDTO.class);
                    pointDTO.setMemberId(memberId);

                    // "상품 구매 확정"인 경우 상품 정보 추가
                    if ("상품 구매 확정".equals(point.getGiveContent())) {
                        String orderCode = point.getOrderCode();

                        // 주문 코드로 주문 아이템 조회 (OrderItemRepository 활용)
                        OrderItem orderItem = orderItemRepository.findFirstByOrder_OrderCode(orderCode);
                        if (orderItem != null && orderItem.getProduct() != null) {
                            Product product = orderItem.getProduct();  // Product 엔티티에 접근
                            pointDTO.setProductImage(product.getImageMain());
                            pointDTO.setProductName(product.getName());
                            pointDTO.setQuantity(orderItem.getQuantity());
                            pointDTO.setPrice(product.getPrice());
                        }
                    }

                    return pointDTO;
                }).collect(Collectors.toList());

        int total = (int) pagePoint.getTotalElements();

        return PointPageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(pointDTOList)
                .total(total)
                .build();
    }

    //고객별 주문내역 불러오기
    public OrderPageResponseDTO getOrderByMemberId(OrderPageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("memberId");
        Page<Tuple> pageOrder = orderRepository.findAllByMember_Id(pageRequestDTO, pageable);

        List<OrderDTO> orderDTOList = pageOrder
                .getContent()
                .stream()
                .map(tuple -> {
                    Order order = tuple.get(0, Order.class);
                    OrderItem orderItem = tuple.get(1, OrderItem.class);
                    Product product = tuple.get(2, Product.class);
                    Member member = tuple.get(3, Member.class);

                    // 상품 정보 설정
                    OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
                    orderDTO.setMember(member);
                    orderDTO.setCompanyName(product.getCompanyName());
                    orderDTO.setDescription(product.getDescription());
                    orderDTO.setPrice(product.getPrice());
                    orderDTO.setPoint(product.getPoint());
                    orderDTO.setProductName(product.getName());
                    orderDTO.setImageList(product.getImageList());
                    orderDTO.setOrderCode(order.getOrderCode());
                    orderDTO.setQuantity(orderItem.getQuantity());
                    orderDTO.setProductCode(orderItem.getProduct().getProductCode());

                    // 판매자 정보 조회
                    String companyName = product.getCompanyName();
                    Seller seller = sellerRepository.findByCompanyName(companyName);

                    // 판매자 정보 설정
                    orderDTO.setRating(seller.getRating());
                    orderDTO.setDelegate(seller.getDelegate());
                    orderDTO.setHp(seller.getHp());
                    orderDTO.setBusinessNo(seller.getBusinessNo());
                    orderDTO.setFax(seller.getFax());
                    orderDTO.setAddr1(seller.getAddr1());
                    orderDTO.setAddr2(seller.getAddr2());
                    orderDTO.setZip(seller.getZip());



                    return orderDTO;
                }).collect(Collectors.toList());

        int total = (int) pageOrder.getTotalElements();

        return OrderPageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(orderDTOList)
                .total(total)
                .build();
    }

    //고객별 리뷰 내역 불러오기
    public ReviewPageResponseDTO getReviewByWriter(ReviewPageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("writer");
        Page<Tuple> pageReview = reviewRepository.findAllByMember_Id(pageRequestDTO, pageable);

        List<ReviewDTO> reviewDTOList = pageReview
                .getContent()
                .stream()
                .map(tuple -> {
                    Review review = tuple.get(0, Review.class);
                    Product product = tuple.get(1, Product.class);
                    Member member = tuple.get(2, Member.class);

                    ReviewDTO reviewDTO = modelMapper.map(review, ReviewDTO.class);
                    MemberDTO memberDTO = modelMapper.map(member, MemberDTO.class);

                    reviewDTO.setProductName(product.getName());
                    reviewDTO.setMember(memberDTO);
                    reviewDTO.setRating(reviewDTO.getRatingStar());

                    return reviewDTO;
                }).collect(Collectors.toList());

        int total = (int) pageReview.getTotalElements();

        return ReviewPageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(reviewDTOList)
                .total(total)
                .build();
    }
    
    //고객별 반품/교환신청 내역 불러오기
    public RefundPageResponseDTO getRefundByMemberId(RefundPageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("memberId");
        String memberId = pageRequestDTO.getMemberId();
        List<Refund> refund = refundRepository.findAllByMember_Id(memberId, pageable);

        List<RefundDTO> refundDTOList = refund
                .stream()
                .map(refund1 -> {
                    RefundDTO refundDTO = modelMapper.map(refund1, RefundDTO.class);
                    refundDTO.setMemberId(memberId);

                    return refundDTO;
                }).toList();

        int total = (int) refundDTOList.size();

        return RefundPageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(refundDTOList)
                .total(total)
                .build();
    }



    /*메인페이지 시작*/    /*메인페이지 시작*/    /*메인페이지 시작*/    /*메인페이지 시작*/

    //마이페이지 메인 문의출력
    public List<QnaDTO> findByMemberIdByLimit3(MemberDTO memberDTO) {
        Member writer = Member
                .builder()
                .id(memberDTO.getId())
                .build();

        List<Qna> qna = qnaRepository.findTop3ByWriterOrderByRegDateDesc(writer);

        List<QnaDTO> qnaDTOList = qna
                .stream()
                .map(qna1 -> {
                    QnaDTO qnaDTO = modelMapper.map(qna1, QnaDTO.class);
                    qnaDTO.setWriter(writer.getId());

                    return qnaDTO;
                })
                .toList();

        return qnaDTOList;

    }

    //최신 주문 내역 불러오기(3)
    public List<OrderDTO> findOrderByMemberIdByLimit3(MemberDTO memberDTO) {
        Member member = memberRepository.findById(memberDTO.getId()).orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

        // 회원에 해당하는 주문 내역만 가져오기
        Page<Tuple> pageOrder = orderRepository.findTop3ByMemberOrderByOrderDateDesc(member);

        List<OrderDTO> orderDTOList = pageOrder
                .getContent()
                .stream()
                .map(tuple -> {
                    Order order = tuple.get(0, Order.class);
                    OrderItem orderItem = tuple.get(1, OrderItem.class);
                    Product product = tuple.get(2, Product.class);

                    // 상품 정보 설정
                    OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
                    orderDTO.setMember(member);
                    orderDTO.setCompanyName(product.getCompanyName());
                    orderDTO.setDescription(product.getDescription());
                    orderDTO.setPrice(product.getPrice());
                    orderDTO.setPoint(product.getPoint());
                    orderDTO.setProductName(product.getName());
                    orderDTO.setImageList(product.getImageList());
                    orderDTO.setOrderCode(order.getOrderCode());
                    orderDTO.setQuantity(orderItem.getQuantity());
                    orderDTO.setProductCode(orderItem.getProduct().getProductCode());

                    // 판매자 정보 조회
                    String companyName = product.getCompanyName();
                    Seller seller = sellerRepository.findByCompanyName(companyName);

                    // 판매자 정보 설정
                    orderDTO.setRating(seller.getRating());
                    orderDTO.setDelegate(seller.getDelegate());
                    orderDTO.setHp(seller.getHp());
                    orderDTO.setBusinessNo(seller.getBusinessNo());
                    orderDTO.setFax(seller.getFax());
                    orderDTO.setAddr1(seller.getAddr1());
                    orderDTO.setAddr2(seller.getAddr2());
                    orderDTO.setZip(seller.getZip());

                    return orderDTO;
                }).collect(Collectors.toList());

        return orderDTOList;
    }


    //최신 포인트 내역 불러오기(3)
    public List<PointDTO> findPointByMemberIdByLimit3(MemberDTO memberDTO){
        Member memberId = Member.builder().id(memberDTO.getId()).build();

        List<Point> point = pointRepository.findTop3ByMemberOrderByGiveDateDesc(memberId);

        List<PointDTO> pointDTOList = point.stream().map(point1 -> {
            PointDTO pointDTO = modelMapper.map(point1, PointDTO.class);
            pointDTO.setMemberId(memberId.getId());

            return pointDTO;
        }).toList();

        return pointDTOList;
    }

    //최신 리뷰 내역 불러오기(3)
    public List<ReviewDTO> findReviewByMemberIdByLimit3(MemberDTO memberDTO){
        Member memberId = Member.builder().id(memberDTO.getId()).build();

        Page<Tuple> pageReview = reviewRepository.findTop3ByMemberOrderByRegDateDesc(memberId);

        List<ReviewDTO> reviewDTOList = pageReview
                .getContent()
                .stream()
                .map(tuple -> {
                    Review review = tuple.get(0, Review.class);
                    Product product = tuple.get(1, Product.class);
                    Member member = tuple.get(2, Member.class);

                    ReviewDTO reviewDTO = modelMapper.map(review, ReviewDTO.class);
                    reviewDTO.setProductName(product.getName());

                    reviewDTO.setMember(memberDTO);

                    return reviewDTO;
                }).toList();

        return reviewDTOList;
    }

    //최근 교환/환불 신청 내역 불러오기(3)
    public List<RefundDTO> getTop3RefundsByMember(String memberId) {
        List<Refund> refundList = refundRepository.findTop3ByMember_IdOrderByRegDateDesc(memberId);

        return refundList.stream()
                .map(refund -> {
                    RefundDTO dto = modelMapper.map(refund, RefundDTO.class);
                    dto.setMemberId(memberId);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    //고객 상품 문의(channel = 판매자 게시판)
    public int qnaWrite(QnaDTO qnaDTO){
        Member member = Member.builder()
                .id(qnaDTO.getWriter())
                .build();

        Qna qna = modelMapper.map(qnaDTO, Qna.class);
        String cate1Name = qnaDTO.getCate1Name();

        qna.setWriter(member);
        qna.setChannel("판매자 게시판");
        qna.setCate2(cate1Name);

        Qna savedQna = qnaRepository.save(qna);

        return savedQna.getQnaNo();
    }

    //주문확정
    @Transactional
    public void confirmPurchase(String orderCode){
        System.out.println(orderCode);

        //주문조회
        Order order = orderRepository.findByOrderCode(orderCode);

        //구매확정으로 변경
        order.setConfirm("구매 확정");

        //회원 정보
        Member member = order.getMember();


        //상품에 대한 포인트 적립
        List<OrderItem> items = orderItemRepository.findByOrder_OrderCode(orderCode);


        for(OrderItem item : items){
            String productCode = item.getProduct().getProductCode();



            Optional<Product> product = productRepository.findByProductCode(productCode);
            if(product.isPresent()){
                int point = product.get().getPoint();

                pointService.addPoint(member, point, "상품 구매 확정", orderCode);
            }

        }

    }

    //상품평쓰기
    @Transactional
    public int writeReview(ReviewDTO reviewDTO){
        Member member = Member.builder()
                .id(reviewDTO.getWriter())
                .build();

        Review review = modelMapper.map(reviewDTO, Review.class);
        review.setMember(member);

        // 이미지 파일이 null이 아닐 때만 파일 이름을 설정
        if (reviewDTO.getImageList1() != null && !reviewDTO.getImageList1().isEmpty()) {
            review.setImage1(reviewDTO.getImageList1().getOriginalFilename());
        }
        if (reviewDTO.getImageList2() != null && !reviewDTO.getImageList2().isEmpty()) {
            review.setImage2(reviewDTO.getImageList2().getOriginalFilename());
        }
        if (reviewDTO.getImageList3() != null && !reviewDTO.getImageList3().isEmpty()) {
            review.setImage3(reviewDTO.getImageList3().getOriginalFilename());
        }

        Review savedReview = reviewRepository.save(review);

        productRepository.incrementViewByProductCode(review.getProductCode());

        return savedReview.getReviewNo();

    }
    
    //교환 및 반품신청
    @Transactional
    public int writeRefund(RefundDTO refundDTO){

        Member member = Member.builder()
                .id((refundDTO.getMemberId()))
                .build();

        Refund refunds = modelMapper.map(refundDTO, Refund.class);
        refunds.setMember(member);

        // 이미지 파일이 null이 아닐 때만 파일 이름을 설정
        if (refundDTO.getImageList1() != null && !refundDTO.getImageList1().isEmpty()) {
            refunds.setImage1(refundDTO.getImageList1().getOriginalFilename());
        }
        if (refundDTO.getImageList2() != null && !refundDTO.getImageList2().isEmpty()) {
            refunds.setImage2(refundDTO.getImageList2().getOriginalFilename());
        }
        if (refundDTO.getImageList3() != null && !refundDTO.getImageList3().isEmpty()) {
            refunds.setImage3(refundDTO.getImageList3().getOriginalFilename());
        }

        Order order = orderRepository.findByOrderCode(refundDTO.getOrderCode());

        if("반품".equals(refundDTO.getChannel())){
            order.setConfirm("반품신청 접수");
            order.setRefundStatus("반품신청");
        } else if ("교환".equals(refundDTO.getChannel())){
            order.setConfirm("교환신청 접수");
            order.setRefundStatus("교환신청");
        }

        orderRepository.save(order);

        Refund savedRefunds = refundRepository.save(refunds);

        return savedRefunds.getRefundNo();
    }

    
    /*메인페이지 끝*/    /*메인페이지 끝*/    /*메인페이지 끝*/    /*메인페이지 끝*/    /*메인페이지 끝*/




    /*헤더 시작*/    /*헤더 시작*/    /*헤더 시작*/    /*헤더 시작*/    /*헤더 시작*/    /*헤더 시작*/

    // Member의 id를 이용해 Qna 총 개수 조회
    public int countQnaByWriter(String writerId) {
        // Qna 게시글 개수 조회
        return qnaRepository.countByWriter_Id(writerId);
    }

    // 최신 포인트 내역 조회
    public int getLatestTotalPoint(String memberId) {
        // 최신 giveDate 기준으로 1개의 totalPoint 가져오기
        List<Integer> points = pointRepository.findLatestTotalPointByMemberId(memberId, PageRequest.of(0, 1));

        // 리스트가 비어있으면 0 반환, 아니면 첫 번째 값을 반환
        return points.isEmpty() ? 0 : points.get(0);
    }

    // 쿠폰 총 갯수 조회
    public int countIssuedByMemberId(String memberId) {
        // 쿠폰 개수 조회
        return issuedCouponRepository.countByMember_Id(memberId);
    }

    // 주문내역 총 갯수 조회
    public int countOrderByMemberId(String memberId) {

        return orderRepository.countByMember_Id(memberId);
    }

    /*헤더 끝*/    /*헤더 끝*/    /*헤더 끝*/    /*헤더 끝*/    /*헤더 끝*/    /*헤더 끝*/


}
