package kr.co.lotteOn.dto.refund;

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
public class RefundDTO {

    private int refundNo;
    private String orderCode;
    private String memberId;
    private String refundType;
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
        if(refundType != null){
            return switch (this.refundType) {
                case "broken" -> "불량/파손";
                case "dislike" -> "단순변심";
                case "misorder" -> "주문실수";
                case "misdelivery" -> "오배송";
                case "misinfo" -> "상품정보 상이";
                case "delay" -> "배송지연";
                case "size" -> "사이즈";
                    default -> "기타";
            };
        }
        return null;
    }

}
