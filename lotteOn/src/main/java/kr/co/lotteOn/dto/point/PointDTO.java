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
    private String orderCode;

    private MemberDTO memberDTO;

    //추가 컬럼
    private String name;

    // 상품 정보 (상품 구매 확정일 때만 세팅)
    private String productImage;
    private String productName;
    private int quantity;
    private int price;
    private String orderDate;


    public String getGiveDate(){
        if(giveDate != null){
            return giveDate.substring(0,10);   // yyyy-mm-dd

        }
        return null;
    }

    public String getOrderCode() {
        if (giveDate == null) return null;  // 날짜가 없으면 null 반환

        String datePart = giveDate.replace("-", ""); // "20250508"
        if (datePart.length() >= 8) {
            datePart = datePart.substring(2,8); // "250508"
        }

        if ("가입 축하 포인트".equals(giveContent)) {
            return "regis" + datePart;
        } else if ("상품 구매 확정".equals(giveContent) && orderCode != null) {
            return orderCode;
        }
        return "etc" + datePart;
    }


}
