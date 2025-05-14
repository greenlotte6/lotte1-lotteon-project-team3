package kr.co.lotteOn.dto.delivery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDTO {

    private int deliveryNo;
    private String orderCode;
    private String receiver;
    private String addr;
    private String post;
    private String waybill;
    private String etc;
    private String regDate;

    public String getRegDate(){
        if(regDate != null){
            return regDate.substring(0,10);   // yyyy-mm-dd

        }
        return null;
    }
}
