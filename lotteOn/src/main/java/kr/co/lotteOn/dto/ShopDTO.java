package kr.co.lotteOn.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopDTO {
    private String sellerId;
    private String shopName;
    private String password;
    private String companyName;
    private String delegate;
    private String businessNo;
    private String communicationNo;
    private String rating;
    private String hp;
    private String fax;
    private String zip;
    private String addr1;
    private String addr2;


}
