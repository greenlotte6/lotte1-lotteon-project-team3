package kr.co.lotteOn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerDTO implements Serializable {
    private String sellerId;
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
    private LocalDateTime regDate;
    private LocalDateTime leaveDate;

}
