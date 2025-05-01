package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Terms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TermsRepository extends JpaRepository<Terms, Integer> {
    Optional<Terms> findByBuyer(String buyer);
    Optional<Terms> findBySeller(String seller);
    Optional<Terms> findByPlace(String place);
    Optional<Terms> findByTrade(String trade);
    Optional<Terms> findByPrivacy(String privacy);
}
