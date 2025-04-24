package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.MemberDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminMemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    public List<MemberDTO> findAll() {
        List<Member> list = memberRepository.findAll();
        return list.stream()
                .map(member -> modelMapper.map(member, MemberDTO.class))
                .collect(Collectors.toList());
    }

    public MemberDTO findById(String id) {
        Optional<Member> optMember = memberRepository.findById(id);

        if (optMember.isPresent()) {
            return modelMapper.map(optMember.get(), MemberDTO.class);
        }

        return null;
    }

    public MemberDTO modify(MemberDTO memberDTO) {
        Optional<Member> optional = memberRepository.findById(memberDTO.getId());

        if (optional.isPresent()) {
            Member member = optional.get();

            // 필요한 값만 수정
            member.setName(memberDTO.getName());
            member.setEmail(memberDTO.getEmail());
            member.setHp(memberDTO.getHp());
            member.setRating(memberDTO.getRating());
            member.setGender(memberDTO.getGender());
            member.setZip(memberDTO.getZip());
            member.setAddr1(memberDTO.getAddr1());
            member.setAddr2(memberDTO.getAddr2());
            member.setAnother(memberDTO.getAnother());

            return modelMapper.map(memberRepository.save(member), MemberDTO.class);
        }

        return null;
    }

    public List<MemberDTO> searchMembers(String type, String keyword) {
        List<Member> result = new ArrayList<>();

        if ("id".equals(type)) {
            result = memberRepository.findByIdContaining(keyword);
        } else if ("name".equals(type)) {
            result = memberRepository.findByNameContaining(keyword);
        } else if ("email".equals(type)) {
            result = memberRepository.findByEmailContaining(keyword);
        } else if ("hp".equals(type)) {
            result = memberRepository.findByHpContaining(keyword);
        }

        return result.stream().map(m -> modelMapper.map(m, MemberDTO.class)).collect(Collectors.toList());
    }
}
