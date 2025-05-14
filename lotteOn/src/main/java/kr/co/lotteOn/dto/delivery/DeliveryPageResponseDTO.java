package kr.co.lotteOn.dto.delivery;


import kr.co.lotteOn.dto.order.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPageResponseDTO {

    private List<DeliveryDTO> dtoList;

    private int pg;
    private int size;
    private int total;
    private int startNo;
    private int start, end;
    private boolean prev, next;

    //search
    private String searchType;
    private String keyword;
    private String orderDate;
    private String receiver;
    private String post;



    //mypage
    private String writer;
    private String memberId;

    @Builder
    public DeliveryPageResponseDTO(DeliveryPageRequestDTO pageRequestDTO, List<DeliveryDTO> dtoList, int total) {
        this.pg = pageRequestDTO.getPg();
        this.size = pageRequestDTO.getSize();
        this.total = total;
        this.dtoList = dtoList;

        this.writer = pageRequestDTO.getWriter();

        //search
        this.searchType = pageRequestDTO.getSearchType();
        this.keyword = pageRequestDTO.getKeyword();
        this.orderDate = pageRequestDTO.getOrderDate();

        this.startNo = total - ((pg - 1) * size);
        this.end = (int) (Math.ceil(this.pg / 10.0)) * 10;
        this.start = this.end - 9;

        int last = (int)(Math.ceil(total / (double)size));
        this.end = end > last ? last : end;
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;

    }
}

