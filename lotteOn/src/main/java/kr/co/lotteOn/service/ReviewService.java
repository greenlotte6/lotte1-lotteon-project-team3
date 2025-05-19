package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.MemberDTO;
import kr.co.lotteOn.dto.review.ReviewDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Product;
import kr.co.lotteOn.entity.Review;
import kr.co.lotteOn.repository.MemberRepository;
import kr.co.lotteOn.repository.ProductRepository;
import kr.co.lotteOn.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductService productService;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void saveReview(ReviewDTO dto) {
        Member member = memberRepository.findById(dto.getWriter())
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));
        Product product = productRepository.findByProductCode(dto.getProductCode())
                .orElseThrow(() -> new IllegalArgumentException("상품 없음"));

        // 리뷰 저장
        Review review = Review.builder()
                .member(member)
                .productCode(dto.getProductCode())
                .title(dto.getTitle())
                .rating(Double.parseDouble(dto.getRating()))
                .content(dto.getContent())
                .image1(dto.getImage1())
                .image2(dto.getImage2())
                .image3(dto.getImage3())
                .build();

        productRepository.incrementViewByProductCode(dto.getProductCode());

    }
/*
    public List<ReviewDTO> getReviewsByProduct(String productCode) {
        List<Review> reviews = reviewRepository.findByProductCode(productCode);

        return reviews.stream()
                .map(review -> ReviewDTO.builder()
                        .reviewNo(review.getReviewNo())
                        .writer(review.getMember().getId()) // 또는 getName()
                        .title(review.getTitle())
                        .rating(review.getRating())
                        .content(review.getContent())
                        .productCode(review.getProductCode())
                        .regDate(review.getRegDate().toString())
                        .image1(review.getImage1())
                        .image2(review.getImage2())
                        .image3(review.getImage3())
                        .member(MemberDTO.fromEntity(review.getMember())) // MemberDTO 있는 경우
                        .productName(productService.getProductNameByCode(review.getProductCode()))
                        .build())
                .toList();
    }
 */

    public List<Review> getReviewsByProductCode(String productCode) {
        return reviewRepository.findByProductCodeOrderByRegDateDesc(productCode);
    }

    public Page<ReviewDTO> getReviewsByProductPaged(String productCode, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findByProductCode(productCode, pageable);
        return reviews.map(ReviewDTO::fromEntity);
    }


}
