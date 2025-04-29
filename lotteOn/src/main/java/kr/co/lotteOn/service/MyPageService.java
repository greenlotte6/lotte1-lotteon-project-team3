package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.MemberDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.repository.FaqRepository;
import kr.co.lotteOn.repository.IssuedCouponRepository;
import kr.co.lotteOn.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyPageService {

    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final FaqRepository faqRepository;
    private final IssuedCouponRepository issuedCouponRepository;

    //정보 출력하기
    public MemberDTO getMemberInfo(String memberId) {
        Optional<Member> optMember = memberRepository.findById(memberId);

        if (optMember.isPresent()) {
            Member member = optMember.get();
            return modelMapper.map(member, MemberDTO.class);
        }

        return null;
    }

    //정보 수정하기
    public void modify(){
    }

    //탈퇴하기

}
