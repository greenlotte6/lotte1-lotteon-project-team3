package kr.co.lotteOn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnDTO {

    private int returnNo;
    private String orderCode;
    private String memberId;
    private String returnType;
    private String reason;
    private String image1;
    private String image2;
    private String image3;

    private String regDate;
    private String status; //반품요청 완료, 반품승인 완료, 상품 회수중, 반품처리 완료

    private String channel; //반품, 교환


    //추가필드
    private MultipartFile imageList1;
    private MultipartFile imageList2;
    private MultipartFile imageList3;

    private MemberDTO member;
    private String productName;


    public String getRegDate(){
        if(regDate != null){
            return regDate.substring(0,10);   // yyyy-mm-dd

        }
        return null;
    }

    public String getReturnType(){
        if(returnType != null){
            return switch (this.returnType) {
                case "broken" -> "불량/파손";
                case "dislike" -> "단순변심";
                case "misorder" -> "주문실수";
                case "misdelivery" -> "오배송";
                case "misinfo" -> "상품정보 상이";
                case "delay" -> "배송지연";
                    default -> "기타";
            };
        }
        return null;
    }

}
