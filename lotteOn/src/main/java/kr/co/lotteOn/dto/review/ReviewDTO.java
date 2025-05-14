package kr.co.lotteOn.dto.review;

import kr.co.lotteOn.dto.MemberDTO;
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

    public String getRatingEmoji(){
        // 기존에 메서드 getRating이 이모지를 반환하는 것으로 의심이 돼서 메서드명을 변경했음.
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
}
