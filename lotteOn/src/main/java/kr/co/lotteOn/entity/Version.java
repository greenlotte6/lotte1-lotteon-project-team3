package kr.co.lotteOn.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Version")
public class Version {

    @Id
    private String versionId;
    private String versionContent;
    private String writer;

    @CreationTimestamp
    private LocalDateTime regDate;
}
