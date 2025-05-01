package kr.co.lotteOn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Qna")
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int qnaNo;    //글번호

    private String cate1;       //카테고리 : 회원, 이벤트, 주문, 배송, 취소, 여행, 안전거래
    private String cate2;       //2차 카테고리
    private String title;       //글제목
    private String content;     //글내용
    private String views;       //조회수
    private String status;      //NULL
    private String comment;
    private String channel;

    @CreationTimestamp
    private LocalDateTime regDate;     //등록날짜

    //private String writer;      //작성자 = 관리자(Admin)
    //추가필드
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="writer")
    private Member writer;

    @PrePersist //Default값 세팅
    public void prePersist() {
        if (this.channel == null) this.channel = "고객센터";
        if (this.status == null) this.status = "답변대기중";
    }

    @PreUpdate
    public void preUpdate() {
        if (this.comment != null && !this.comment.trim().isEmpty()) {
            this.status = "답변완료";
        }
    }
}

