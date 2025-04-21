package kr.co.lotteOn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {

    private String id;
    private String password;
    private String name;
    private String gender;
    private String email;
    private String hp;
    private String rating;
    private String role;
    private String zip;
    private String addr1;
    private String addr2;
    private String regDate;
    private String leaveDate;
}
