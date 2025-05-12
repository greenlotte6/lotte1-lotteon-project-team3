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
}
