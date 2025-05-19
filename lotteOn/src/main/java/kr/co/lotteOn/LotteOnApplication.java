package kr.co.lotteOn;

import kr.co.lotteOn.config.AppInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(AppInfo.class)
public class LotteOnApplication {
	public static void main(String[] args) {
		SpringApplication.run(LotteOnApplication.class, args);
	}
}
