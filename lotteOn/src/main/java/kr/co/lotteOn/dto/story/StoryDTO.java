package kr.co.lotteOn.dto.story;

import kr.co.lotteOn.dto.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoryDTO {

    public int storyNo;

    public String cate;
    public String title;
    public String content;
    public MultipartFile imageMainFile; //이미지 업로드용
    public String imageMain; //이미지 경로 저장용 - DB
    public String writer;
    private String regDate;     //등록날짜

    //추가필드
    private MemberDTO member;

    public String getRegDate(){
        if(regDate != null){
            return regDate.substring(0,10);   // yyyy-mm-dd

        }
        return null;
    }

    // 자동 매핑을 위한 메서드 추가
    public String getCateName() {
        switch (this.cate) {
            case "culture": return "기업문화";
            case "interview": return "인터뷰";
            case "recruit": return "채용";
            case "lotteOn": return "롯데ON";
            case "press": return "보도자료";
            case "esg": return "ESG";
            default: return "알 수 없음";
        }
    }

}
