package kr.co.lotteOn.controller.test;

import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.repository.MemberRepository;
import kr.co.lotteOn.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PointTestController {

    private final MemberRepository memberRepository;
    private final PointService pointService;

    @GetMapping("/test/addPoint")
    public String testAddPoint(@RequestParam String memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member == null) return "회원 없음";

        pointService.addPoint(member, 500, "테스트 포인트");

        return "포인트 적립 완료";
    }
}