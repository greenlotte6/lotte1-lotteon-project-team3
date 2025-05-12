package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.ReviewDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Product;
import kr.co.lotteOn.entity.Review;
import kr.co.lotteOn.repository.MemberRepository;
import kr.co.lotteOn.repository.ProductRepository;
import kr.co.lotteOn.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
                .rating(dto.getRating())
                .content(dto.getContent())
                .image1(dto.getImage1())
                .image2(dto.getImage2())
                .image3(dto.getImage3())
                .build();

        reviewRepository.save(review);

        // 상품 조회수 +1
        product.setViews(product.getViews() + 1);
    }

    public List<Review> getReviewsByProduct(String productCode) {
        return reviewRepository.findByProductCode(productCode);
    }

}
