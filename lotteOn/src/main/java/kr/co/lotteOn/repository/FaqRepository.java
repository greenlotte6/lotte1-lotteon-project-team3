package kr.co.lotteOn.repository;

import kr.co.lotteOn.entity.Faq;
import kr.co.lotteOn.repository.custom.FaqRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Integer>, FaqRepositoryCustom {

    public Page<Faq> findAllByCate1AndCate2(String cate1, String cate2, Pageable pageable);

    public Page<Faq> findAllByCate1(String cate1, Pageable pageable);
}
