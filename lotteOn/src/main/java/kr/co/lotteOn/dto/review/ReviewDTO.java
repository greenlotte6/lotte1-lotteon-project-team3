package kr.co.lotteOn.dto.review;

import kr.co.lotteOn.dto.MemberDTO;
import kr.co.lotteOn.dto.ProductDTO;
import kr.co.lotteOn.entity.Product;
import kr.co.lotteOn.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {

    private int reviewNo;

    private String writer;

    private String title;
    private String rating;
    private String content;
    private String productCode;

    //경로 저장용
    private String image1;
    private String image2;
    private String image3;

    private String regDate;

    //추가필드
    private MemberDTO member;
    private String productName;

    //업로드용
    private MultipartFile imageList1;
    private MultipartFile imageList2;
    private MultipartFile imageList3;


    public String getRegDate(){
        if(regDate != null){
            return regDate.substring(0,10);   // yyyy-mm-dd

        }
        return null;
    }

    public String getRatingStar(){
        if(rating != null){
            return switch (this.rating) {
                case "1" -> "⭐";
                case "2" -> "⭐⭐";
                case "3" -> "⭐⭐⭐";
                case "4" -> "⭐⭐⭐⭐";
                default -> "⭐⭐⭐⭐⭐";
            };
        }
        return null;
    }
    public int getRatingNumber() {
        try {
            return Integer.parseInt(rating);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static ReviewDTO fromEntity(Review review) {
        return ReviewDTO.builder()
                .reviewNo(review.getReviewNo())
                .writer(review.getMember().getName()) // Member 이름 추출
                .title(review.getTitle())
                .rating(String.valueOf(review.getRating()))
                .content(review.getContent())
                .productCode(review.getProductCode())
                .image1(review.getImage1())
                .image2(review.getImage2())
                .image3(review.getImage3())
                .regDate(review.getRegDate() != null ? review.getRegDate().toString() : null)
                .member(MemberDTO.fromEntity(review.getMember())) // Member 객체 전체도 DTO로 포함
                .build();
    }

}
