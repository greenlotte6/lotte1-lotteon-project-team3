package kr.co.lotteOn.dto.coupon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponDTO {

    private String couponCode;  //쿠폰 코드 -> 자동생성

    private String couponType;  //쿠폰종류
    private String couponName;  //쿠폰명
    private int issuedNo;   //쿠폰 고유 번호
    private String benefit;     //혜택
    private String startDate;   //사용기간 시작
    private String endDate;     //사용기간 종료
    private String companyName; //발급자(회사)
    private int issueCount; //발급수
    private int useCount;   //사용수
    private String status;  //발급중, 발급종료
    private String issuedDate;   //발급일

    private String management;
    private String etc; //쿠폰 추가 정보(유의사항 등)


    //날짜 잘라내기
    public String getIssuedDate(){
        if(issuedDate != null){
            return issuedDate.substring(0,10);   // yyyy-mm-dd

        }
        return null;
    }

    public int extractNumber() {
        if (benefit == null || benefit.isEmpty()) {
            throw new IllegalArgumentException("Benefit 값이 없습니다.");
        }

        // 숫자만 추출
        String numberOnly = benefit.replaceAll("[^0-9]", "");
        if (numberOnly.isEmpty()) {
            throw new IllegalArgumentException("Benefit 안에 숫자가 없습니다: " + benefit);
        }

        return Integer.parseInt(numberOnly);
    }

    // 자동 매핑을 위한 메서드 추가
    public String getCouponType() {
        return switch (this.couponType) {
            case "each" -> "개별상품 할인";
            case "order" -> "주문상품 할인";
            case "free" -> "배송비 무료";
            case "개별상품 할인" -> "개별상품 할인";
            case "주문상품 할인" -> "주문상품 할인";
            case "배송비 무료" -> "배송비 무료";
            default -> "알 수 없음";
        };
    }



}
