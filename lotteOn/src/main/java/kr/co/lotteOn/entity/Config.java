package kr.co.lotteOn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Config")
public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int configNo;

    // 사이트
    private String title;
    private String subTitle;

    // 로고
    private String headerLogo;
    private String footerLogo;
    private String favicon;

    // 기업 정보
    private String companyName;
    private String ceoName;
    private String businessNo;
    private String communicationNo;
    private String addr1;
    private String addr2;

    // 고객센터 정보
    private String hp;
    private String businessHours;
    private String email;
    private String eft; //전자금융거래 분쟁담당

    // 카피라이트
    private String copyright;
}
