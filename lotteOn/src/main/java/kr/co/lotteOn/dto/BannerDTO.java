package kr.co.lotteOn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BannerDTO {

    private String name;
    private String sliderTitle;
    private String sliderSubTitle;
    private String size;
    private String backgroundColor;
    private String link;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private MultipartFile imageFile;
}
