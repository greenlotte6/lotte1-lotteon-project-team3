package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.OrderRequestDTO;
import kr.co.lotteOn.dto.OrderResultDTO;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Order;
import kr.co.lotteOn.repository.MemberRepository;
import kr.co.lotteOn.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    public OrderResultDTO createOrder(OrderRequestDTO requestDTO) {

        // Member 조회
        Member member = memberRepository.findById(requestDTO.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // DTO -> Entity
        Order order = requestDTO.toEntity(member);

        // 저장
        Order savedOrder = orderRepository.save(order);

        // Entity -> ResultDTO
        return OrderResultDTO.fromEntity(savedOrder);
    }

}
