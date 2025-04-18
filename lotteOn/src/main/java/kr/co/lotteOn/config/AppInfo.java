package kr.co.lotteOn.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppInfo {

    @Value("${spring.application.name}")   //application.yml 파일에 속성값으로 초기화
    private String appName;

    @Value("${spring.application.version}")
    private String appVersion;

}
