package kr.co.lotteOn.dto.order;

import kr.co.lotteOn.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {

    private String orderCode;
    private Member member;
    private String name;
    private String productCode;
    private int totalPrice;
    private String payment;
    private String orderStatus;
    private String orderDate;
    private String delivery;
    private String discount;
    private String fee;
    private String actualMoney;
    private String confirm;
    private String receiver;

    //추가필드
    private String companyName;
    private String productName;
    private String description;
    private int point;
    private String imageList;
    private int price;
    private int quantity;
    private String rating;
    private String delegate;
    private String hp;
    private String businessNo;
    private String fax;
    private String addr1;
    private String addr2;
    private String zip;

    private String memberId;
    private String memberName;

    public String getOrderDate(){
        if(orderDate != null){
            return orderDate.substring(0,10);   // yyyy-mm-dd

        }
        return null;
    }

    public String getPaymentName(){
        switch (this.payment) {
            case "tosspay": return "토스페이";
            case "creditCard": return "신용카드";
            case "kakaopay": return "카카오페이";
            case "phone": return "휴대폰결제";
            default: return null;
        }
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

}

