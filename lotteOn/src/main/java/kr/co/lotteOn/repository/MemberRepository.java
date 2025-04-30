package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Member;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    List<Member> findByIdContaining(String keyword);
    List<Member> findByNameContaining(String keyword);
    List<Member> findByEmailContaining(String keyword);
    List<Member> findByHpContaining(String keyword);

    @Modifying
    @Query("UPDATE Member m SET m.visitDate = CURRENT_TIMESTAMP WHERE m.id = :id")
    void updateVisitDate(@Param("id") String id);

    boolean existsById(String id);
    boolean existsByHp(String hp);
    boolean existsByEmail(String email);

    Optional<Member> findById(String id);
    Optional<Member> findByEmail(String email);
    boolean existsByNameAndEmail(String name, String email);
    boolean existsByIdAndEmail(String id, String email);
}
