package kr.co.lotteOn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TermsDTO {
    private int termsId;
    private String buyer;
    private String seller;
    private String trade;
    private String place;
    private String privacy;
}
