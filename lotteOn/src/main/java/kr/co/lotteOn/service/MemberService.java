package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.MemberDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class MemberService {

    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;

    public void register(MemberDTO memberDTO) {

        Member member = modelMapper.map(memberDTO, Member.class);

        memberRepository.save(member);


    }



}
