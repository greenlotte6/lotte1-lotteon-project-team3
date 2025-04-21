package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.MemberDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class MemberService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public void register(MemberDTO memberDTO) {
        // 비밀번호 암호화
        String encodedPass = passwordEncoder.encode(memberDTO.getPassword());
        memberDTO.setPassword(encodedPass);
        
        // 엔티티 변환
        Member member = modelMapper.map(memberDTO, Member.class);
        
        // 저장
        memberRepository.save(member);
    }



}
