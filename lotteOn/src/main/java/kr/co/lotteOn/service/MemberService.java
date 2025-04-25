package kr.co.lotteOn.service;

import jakarta.transaction.Transactional;
import kr.co.lotteOn.dto.MemberDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class MemberService {

    private final ModelMapper modelMapper;

    @Lazy
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public boolean isIdExist(String id) {
        return memberRepository.existsById(id);
    }

    public void register(MemberDTO memberDTO) {
        // 비밀번호 암호화
        String encodedPass = passwordEncoder.encode(memberDTO.getPassword());
        memberDTO.setPassword(encodedPass);
        
        // 엔티티 변환
        Member member = modelMapper.map(memberDTO, Member.class);
        
        // 저장
        memberRepository.save(member);
    }

    //이름 존재 여부 확인
    public boolean existsByName(String name) {
        return memberRepository.existsByName(name);
    }
    //이메일 존재 여부 확인
    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional
    public void updateVisitDate(String memberId) {
        memberRepository.updateVisitDate(memberId);
    }

}
