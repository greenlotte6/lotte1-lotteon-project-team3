package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.VersionDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Version;
import kr.co.lotteOn.repository.MemberRepository;
import kr.co.lotteOn.repository.VersionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class VersionService {

    private final VersionRepository versionRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    public void registerVersion(VersionDTO versionDTO) {

        // 엔티티 변환
        Version version = modelMapper.map(versionDTO, Version.class);

        // 현재 로그인한 사용자 정보 가져오기
        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();

        // 로그인한 사용자 정보 가져오기
        Optional<Member> member = memberRepository.findById(memberId);

        // 로그인한 사용자가 존재하는 경우에만 이름을 writer에 세팅
        if (member.isPresent()) {
            version.setWriter(member.get().getName()); // member 객체에서 이름을 꺼내서 세팅
        } else {
            throw new RuntimeException("User not found");
        }

        // 저장
        versionRepository.save(version);
    }

    public List<VersionDTO> getAllVersions() {
        List<Version> list = versionRepository.findAllByOrderByRegDateDesc();
        return list.stream()
                .map(version -> modelMapper.map(version, VersionDTO.class))
                .collect(Collectors.toList());
    }

    public String getLatestVersionId() {
        return versionRepository.findTopByOrderByRegDateDesc()
                .map(Version::getVersionId)
                .orElse("N/A"); // 없을 경우 기본값
    }

    public void deleteVersionsByIds(List<String> versionIds) {
            versionRepository.deleteByVersionIdIn(versionIds);  // 이 부분이 실제 DB에서 삭제하는 부분
    }
}
