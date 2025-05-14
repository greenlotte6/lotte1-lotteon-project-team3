package kr.co.lotteOn.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class SalesDTO {

    private String companyName;
    private String businessNo;
    private int payDone;


    //추가
    private int orderCount;
    private int orderTotal;
    private int salesTotal;

    //추가
    public SalesDTO(String companyName, String businessNo){
        this.companyName = companyName;
        this.businessNo = businessNo;
    }
}
