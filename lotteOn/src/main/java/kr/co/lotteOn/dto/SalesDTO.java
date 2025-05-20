package kr.co.lotteOn.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class SalesDTO implements Serializable {

    private String companyName;
    private String businessNo;
    private int payDone;


    //추가
    private int orderCount;
    private int orderTotal;
    private int salesTotal;
    private Long purchaseDone;


    //추가
    public SalesDTO(String companyName, String businessNo){
        this.companyName = companyName;
        this.businessNo = businessNo;
    }
}
