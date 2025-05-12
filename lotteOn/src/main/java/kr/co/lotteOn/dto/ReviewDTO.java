package kr.co.lotteOn.dto;

import jakarta.persistence.*;
import kr.co.lotteOn.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


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
    private String image1;
    private String image2;
    private String image3;

    private String regDate;


    //추가필드
    private MemberDTO member;

    public String getRegDate(){
        if(regDate != null){
            return regDate.substring(0,10);   // yyyy-mm-dd

        }
        return null;
    }
    private String productCode;
}
