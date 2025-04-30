package kr.co.lotteOn.dto.issuedCoupon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IssuedCouponPageRequestDTO {

    @Builder.Default    //기본값 초기화
    private int no = 1;

    @Builder.Default    //기본값 초기화
    private int pg = 1;

    @Builder.Default    //기본값 초기화
    private int size = 10;

    private String searchType;
    private String keyword;
    private String cate;

    private String memberId;

    //글목록 페이징 처리를 위한 Pageable 객체 생성 메서드
    public Pageable getPageable(String sort){
        return PageRequest.of(this.pg-1, this.size, Sort.by(sort).descending());
    }




}
