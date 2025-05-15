package kr.co.lotteOn.dto.order;

import kr.co.lotteOn.dto.OrderItemDTO;
import kr.co.lotteOn.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private Long actualMoney;
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
    private String etc;
    private String refundStatus;

    //배송정보 추가필드
    private String post;
    private String addr;
    private String waybill;
    private String regDate;

    //회원정보
    private String memberId;
    private String memberName;
    private String displayProductName;

    public String getOrderDate(){
        if(orderDate != null){
            return orderDate.substring(0,10);   // yyyy-mm-dd

        }
        return null;
    }

    public String getRegDate(){
        if(regDate != null){
            return regDate.substring(0,10);   // yyyy-mm-dd

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


    public String getDisplayProductName() {
        String baseName = productName;

        // 10글자 초과 시 "..." 처리
        if (baseName != null && baseName.length() > 10) {
            baseName = baseName.substring(0, 10) + "...";
        }

        // 수량이 2개 이상일 경우 " 외 N건" 붙이기
        if (quantity > 1) {
            return baseName + " 외 " + (quantity - 1) + "건";
        }

        return baseName;
    }

    public void setProductInfo(Product product, OrderItem item) {
        this.companyName = product.getCompanyName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.point = product.getPoint();
        this.productName = product.getName();
        this.imageList = product.getImageList();
        this.productCode = item.getProduct().getProductCode();
        this.quantity = item.getQuantity();
        this.totalPrice = product.getPrice() * item.getQuantity();
    }

    public void setSellerInfo(Seller seller) {
        this.rating = seller.getRating();
        this.delegate = seller.getDelegate();
        this.hp = seller.getHp();
        this.businessNo = seller.getBusinessNo();
        this.fax = seller.getFax();
        this.addr1 = seller.getAddr1();
        this.addr2 = seller.getAddr2();
        this.zip = seller.getZip();
    }

    public void setDeliveryInfo(Delivery delivery){
        this.post = delivery.getPost();
        this.addr = delivery.getAddr();
        this.waybill = delivery.getWaybill();
        this.regDate = String.valueOf(delivery.getRegDate());

    }

}

