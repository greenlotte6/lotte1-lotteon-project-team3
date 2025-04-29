package kr.co.lotteOn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VersionDTO {

    private String versionId;
    private String versionContent;
    private String writer;
    private LocalDateTime regDate;

    public String getFormattedRegDate() {
        return regDate != null ? regDate.toString().substring(0, 10) : "";
    }
}
