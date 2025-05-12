package kr.co.lotteOn.service;

import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Point;
import kr.co.lotteOn.repository.MemberRepository;
import kr.co.lotteOn.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class PointService {

    public final PointRepository pointRepository;
    private final MemberRepository memberRepository;

    public void addPoint(Member member, int givePoint, String giveContent){
        //최신 포인트 조회
        Point latestPoint = pointRepository.findTopByMemberOrderByGiveDateDesc(member);
        int previousPoint = (latestPoint != null) ? latestPoint.getTotalPoint() : 0;

        //새로운 누적 포인트 계산
        int newPoint = previousPoint + givePoint;

        Point point = Point.builder()
                .member(member)
                .givePoint(givePoint)
                .totalPoint(newPoint)
                .giveContent(giveContent)
                .build();

        pointRepository.save(point);

    }

    // 주문번호를 저장하는 오버로딩 메서드
    public void addPoint(Member member, int givePoint, String giveContent, String orderCode){
        Point latestPoint = pointRepository.findTopByMemberOrderByGiveDateDesc(member);
        int previousPoint = (latestPoint != null) ? latestPoint.getTotalPoint() : 0;
        int newPoint = previousPoint + givePoint;

        Point point = Point.builder()
                .member(member)
                .givePoint(givePoint)
                .totalPoint(newPoint)
                .giveContent(giveContent)
                .orderCode(orderCode) // 주문번호 저장
                .build();

        pointRepository.save(point);
    }

    public Point getLatestPoint(Member member){
        return pointRepository.findTopByMemberOrderByGiveDateDesc(member);
    }

    public void usePoint(String memberId, int amount, String orderCode){
        if (amount <= 0) return;

        // 1. 최신 누적 포인트 가져오기 (최근 사용 내역 기준)
        int currentTotal = pointRepository.findTopByMember_IdOrderByGiveDateDesc(memberId)
                .map(Point::getTotalPoint)
                .orElse(0);

        // 2. 회원 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        // 3. 포인트 차감 내역 생성
        Point point = Point.builder()
                .member(member)
                .type("USE")
                .givePoint(-amount)
                .totalPoint(currentTotal - amount)
                .giveContent("주문 사용")
                .orderCode(orderCode)
                .build();

        pointRepository.save(point);
    }
}
