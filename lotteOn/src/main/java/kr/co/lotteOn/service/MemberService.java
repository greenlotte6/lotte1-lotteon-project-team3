package kr.co.lotteOn.service;

import jakarta.transaction.Transactional;
import kr.co.lotteOn.dto.MemberDTO;
import kr.co.lotteOn.dto.point.PointDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Point;
import kr.co.lotteOn.repository.MemberRepository;
import kr.co.lotteOn.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class MemberService {

    private final ModelMapper modelMapper;

    @Lazy
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final PointRepository pointRepository;

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

        //회원가입 감사 포인트 DTO 생성
        Point point = new Point();
        point.setMember(member); // 반드시 지정해야 함
        point.setGiveContent("가입 축하 포인트");
        point.setGiveDate(LocalDateTime.now());
        point.setGivePoint(1000);
        point.setTotalPoint(1000);

        pointRepository.save(point);
    }

    //휴대폰 존재 여부 확인
    public boolean existsByHp(String hp) {
        return memberRepository.existsByHp(hp);
    }
    //이메일 존재 여부 확인
    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional
    public void updateVisitDate(String memberId) {
        memberRepository.updateVisitDate(memberId);
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> findById(String id) {
        return memberRepository.findById(id);
    }

    public Member save(Member member) {
        return memberRepository.save(member);
    }
}
