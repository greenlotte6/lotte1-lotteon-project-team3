package kr.co.lotteOn.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

    @Bean(name = "jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("rkxe63LC5xAFjH3");  // 암호화 키 설정
        encryptor.setAlgorithm("PBEWithMD5AndDES");  // 기본 암호화 알고리즘
        return encryptor;
    }
}
