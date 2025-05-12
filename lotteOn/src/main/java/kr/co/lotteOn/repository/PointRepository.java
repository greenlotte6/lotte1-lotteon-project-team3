package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Point;
import kr.co.lotteOn.repository.custom.PointRepositoryCustom;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PointRepository extends JpaRepository<Point, Integer>, PointRepositoryCustom {

    // 최신 giveDate 기준으로 유저의 totalPoint를 가져오는 쿼리
    @Query("SELECT p.totalPoint FROM Point p WHERE p.member.id = :memberId ORDER BY p.giveDate DESC")
    List<Integer> findLatestTotalPointByMemberId(@Param("memberId") String memberId, Pageable pageable);


    public Point findTopByMemberOrderByGiveDateDesc(Member member);

    public List<Point> findTop3ByMemberOrderByGiveDateDesc(Member memberId);

    Optional<Point> findTopByMember_IdOrderByGiveDateDesc(String memberId);
}
