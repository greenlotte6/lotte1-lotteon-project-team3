package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    List<Member> findByIdContaining(String keyword);
    List<Member> findByNameContaining(String keyword);
    List<Member> findByEmailContaining(String keyword);
    List<Member> findByHpContaining(String keyword);
}
