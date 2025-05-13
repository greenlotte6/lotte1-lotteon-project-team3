package kr.co.lotteOn.dto.refund;


import kr.co.lotteOn.dto.review.ReviewDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundPageResponseDTO {

    private List<RefundDTO> dtoList;

    private int pg;
    private int size;
    private int total;
    private int startNo;
    private int start, end;
    private boolean prev, next;


    //mypage
    private String memberId;

    @Builder
    public RefundPageResponseDTO(RefundPageRequestDTO pageRequestDTO, List<RefundDTO> dtoList, int total) {
        this.pg = pageRequestDTO.getPg();
        this.size = pageRequestDTO.getSize();
        this.total = total;
        this.dtoList = dtoList;

        this.memberId = pageRequestDTO.getMemberId();


        this.startNo = total - ((pg - 1) * size);
        this.end = (int) (Math.ceil(this.pg / 10.0)) * 10;
        this.start = this.end - 9;

        int last = (int)(Math.ceil(total / (double)size));
        this.end = end > last ? last : end;
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;

    }
}

