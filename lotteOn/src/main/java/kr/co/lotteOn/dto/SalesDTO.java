package kr.co.lotteOn.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SalesDTO {

    private int salesNo;
    private String companyName;
    private String businessNo;
    private String orderCount;

    public SalesDTO(int salesNo, String companyName, String businessNo) {
        this.salesNo = salesNo;
        this.companyName = companyName;
        this.businessNo = businessNo;
    }

    public SalesDTO(String companyName, int orderCount) {
    }

    public void add(SalesDTO salesDTO) {
    }
}
