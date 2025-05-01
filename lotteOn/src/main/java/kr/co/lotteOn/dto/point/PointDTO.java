package kr.co.lotteOn.dto.point;

import jakarta.persistence.*;
import kr.co.lotteOn.dto.MemberDTO;
import kr.co.lotteOn.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointDTO {

    private int pointNo;
    private String memberId;
    private int givePoint;
    private int totalPoint;
    private String giveContent;
    private String giveDate;

    private MemberDTO memberDTO;

    //추가 컬럼
    private String name;


    public String getGiveDate(){
        if(giveDate != null){
            return giveDate.substring(0,10);   // yyyy-mm-dd

        }
        return null;
    }


}
