package kr.co.lotteOn.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopDTO {
    private int shopId; //아이디
    private String companyName; //회사명
    private String delegate;    //대표자
    private String businessNo;  //사업자등록번호
    private String communicationNo; //통신판매업번호
    private String shopHp;  //상점 전화번호
    private String status;  // 상점 상태
    private String management; // 관리 담당자


}
