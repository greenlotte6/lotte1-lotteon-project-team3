package kr.co.lotteOn.repository;

import kr.co.lotteOn.dto.SalesDTO;
import kr.co.lotteOn.entity.Sales;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Integer> {


}
