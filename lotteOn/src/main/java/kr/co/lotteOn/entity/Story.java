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
@Table(name = "Story")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int storyNo;

    public String cate;
    public String title;
    public String content;
    public String imageMain;
    //public String writer;
    //추가필드
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="writer")
    private Member writer;

    @CreationTimestamp
    private LocalDateTime regDate;     //등록날짜

}
