package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.refund.RefundDTO;
import kr.co.lotteOn.dto.refund.RefundPageRequestDTO;
import kr.co.lotteOn.dto.refund.RefundPageResponseDTO;
import kr.co.lotteOn.entity.Refund;
import kr.co.lotteOn.repository.MemberRepository;
import kr.co.lotteOn.repository.RefundRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminOrderService {
    private final MemberRepository memberRepository;
    private final RefundRepository refundRepository;
    private final ModelMapper modelMapper;

    //관리자 환불/교환 신청 내역 불러오기
    public RefundPageResponseDTO RefundList(RefundPageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("refundNo");
        Page<Refund> pageRefund = refundRepository.findAll(pageable);

        List<RefundDTO> refundList = pageRefund
                .stream()
                .map(refund -> {
                    RefundDTO refundDTO = modelMapper.map(refund, RefundDTO.class);

                    refundDTO.setMemberId(refund.getMember().getId());

                    return refundDTO;
                }).toList();

        int total = (int) pageRefund.getTotalElements();

        return RefundPageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(refundList)
                .total(total)
                .build();
    }
}
