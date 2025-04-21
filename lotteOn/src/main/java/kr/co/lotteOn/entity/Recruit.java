package kr.co.lotteOn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Recruit")
public class Recruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recruitNo;    //글번호

    private String cate;        //채용부서
    private String experience;  //경력
    private String employType;  //채용형태
    private String title;       //글제목
    private String writer;      //작성자
    private String content;     //글내용
    private String startDate;     //시작날짜
    private String endDate;     //종료날짜
    private String regDate;     //등록날짜
    private String status;      //채용중, 채용마감


}
