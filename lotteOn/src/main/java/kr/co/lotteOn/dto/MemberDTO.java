package kr.co.lotteOn.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import kr.co.lotteOn.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {

    @NotBlank(message = "아이디는 필수입니다.")
    private String id;
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,12}$",
            message = "비밀번호는 영문+숫자+특수문자 포함 8~12자여야 합니다.")
    private String password;
    @NotBlank(message = "이름은 필수입니다.")
    private String name;
    private String gender;
    private LocalDate birthDate;
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    @NotBlank(message = "휴대폰 번호는 필수입니다.")
    private String hp;
    private String rating;
    private String role;
    private String status;
    @NotBlank(message = "우편번호는 필수입니다.")
    private String zip;
    @NotBlank(message = "기본 주소는 필수입니다.")
    private String addr1;
    @NotBlank(message = "상세 주소는 필수입니다.")
    private String addr2;
    private String another;
    private String regDate;
    private String leaveDate;
    private String visitDate;

    // OAuth 인증 업체 정보
    private String provider; // 소셜 로그인 제공자 (Naver, Google 등)
    private String providerId;    // 소셜 로그인 제공자에서 제공하는 고유 ID

    public String getFormattedRegDate() {
        return regDate != null ? regDate.toString().substring(0, 10) : "";
    }
    public String getFormattedVisitDate() {
        return visitDate != null ? visitDate.toString().substring(0, 10) : "";
    }

    public static MemberDTO fromEntity(Member member) {
        if (member == null) return null;
        return MemberDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .gender(member.getGender())
                .birthDate(member.getBirthDate())
                .email(member.getEmail())
                .hp(member.getHp())
                .build();
    }
}
