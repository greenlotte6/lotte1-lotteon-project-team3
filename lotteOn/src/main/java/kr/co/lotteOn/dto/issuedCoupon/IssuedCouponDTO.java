package kr.co.lotteOn.dto.issuedCoupon;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssuedCouponDTO {

    private int issuedNo;

    private String couponCode;
    private String memberId;
    private String useDate;
    private String status;

    //추가 컬럼
    private String companyName;
    private String couponName;
    private String benefit;
    private String couponType;
    private String etc;
    private String startDate;
    private String endDate;


    //날짜 잘라내기
    public String getUseDate(){
        if(useDate != null){
            return useDate.substring(0,10);   // yyyy-mm-dd

        }
        return null;
    }

}
