package kr.co.lotteOn.service;

import jakarta.persistence.EntityNotFoundException;
import kr.co.lotteOn.dto.ConfigDTO;
import kr.co.lotteOn.entity.Config;
import kr.co.lotteOn.repository.ConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ConfigService {

    private final ConfigRepository configRepository;

    public Config findById(int id) {
        return configRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Config not found"));
    }

    public void updateSiteInfo(ConfigDTO dto) {
        Config config = configRepository.findById(1).orElseThrow();
        config.setTitle(dto.getTitle());
        config.setSubTitle(dto.getSubTitle());
        configRepository.save(config);
    }

    public void updateLogos(MultipartFile headerLogo, MultipartFile footerLogo, MultipartFile favicon) throws IOException {
        // Config 엔티티 불러오기
        Config config = configRepository.findById(1)
                .orElseThrow(() -> new NoSuchElementException("설정 정보가 없습니다."));

        String uploadDir = "src/main/resources/static/images/config"; // 로고 파일 저장 경로

        // 파일 업로드 및 파일명 DB에 저장
        if (!headerLogo.isEmpty()) {
            String fileName = headerLogo.getOriginalFilename();
            headerLogo.transferTo(Paths.get(uploadDir, fileName));
            config.setHeaderLogo(fileName);  // 파일명 저장
        }

        if (!footerLogo.isEmpty()) {
            String fileName = footerLogo.getOriginalFilename();
            footerLogo.transferTo(Paths.get(uploadDir, fileName));
            config.setFooterLogo(fileName);  // 파일명 저장
        }

        if (!favicon.isEmpty()) {
            String fileName = favicon.getOriginalFilename();
            favicon.transferTo(Paths.get(uploadDir, fileName));
            config.setFavicon(fileName);  // 파일명 저장
        }

        // Config 엔티티 업데이트
        configRepository.save(config);  // DB에 업데이트
    }

    public void updateCompanyInfo(ConfigDTO dto) {
        Config config = configRepository.findById(1).orElseThrow();
        config.setCompanyName(dto.getCompanyName());
        config.setCeoName(dto.getCeoName());
        config.setBusinessNo(dto.getBusinessNo());
        config.setCommunicationNo(dto.getCommunicationNo());
        config.setAddr1(dto.getAddr1());
        config.setAddr2(dto.getAddr2());
        configRepository.save(config);
    }

    public void updateCustomerServiceInfo(ConfigDTO dto) {
        Config config = configRepository.findById(1).orElseThrow();
        config.setHp(dto.getHp());
        config.setBusinessHours(dto.getBusinessHours());
        config.setEmail(dto.getEmail());
        config.setEft(dto.getEft());
        configRepository.save(config);
    }

    public void updateCopyright(ConfigDTO dto) {
        Config config = configRepository.findById(1).orElseThrow();
        config.setCopyright(dto.getCopyright());
        configRepository.save(config);
    }

}
