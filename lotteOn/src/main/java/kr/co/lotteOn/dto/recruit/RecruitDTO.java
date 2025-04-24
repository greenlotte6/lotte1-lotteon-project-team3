package kr.co.lotteOn.dto.recruit;

import kr.co.lotteOn.dto.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecruitDTO {

    private int recruitNo;    //글번호

    private String cate;        //채용부서
    private String experience;  //경력
    private String employType;  //채용형태
    private String title;       //글제목
    private String writer;      //작성자
    private String etc;     //글내용
    private String startDate;     //시작날짜
    private String endDate;     //종료날짜
    private String regDate;     //등록날짜
    private String status;      //채용중, 채용마감

    //추가필드
    private MemberDTO member;

    public String getRegDate(){
        if(regDate != null){
            return regDate.substring(0,10);   // yyyy-mm-dd

        }
        return null;
    }

    // 자동 매핑을 위한 메서드 추가
    public String getExperienceYear() {
        switch (this.experience) {
            case "경력무관": return "경력무관";
            case "2": return "1~2년차";
            case "3": return "2~3년차";
            case "4": return "3~4년차";
            case "5": return "4~5년차";
            case "6": return "5년 이상";
            case "10": return "10년 이상";
            default: return "알 수 없음";
        }
    }


}
