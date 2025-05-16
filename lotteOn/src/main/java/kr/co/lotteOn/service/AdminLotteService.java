package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.notice.NoticeDTO;
import kr.co.lotteOn.dto.qna.QnaDTO;
import kr.co.lotteOn.entity.Notice;
import kr.co.lotteOn.entity.Qna;
import kr.co.lotteOn.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminLotteService {

    private final NoticeRepository noticeRepository;
    private final ModelMapper modelMapper;
    private final QnaRepository qnaRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final RefundRepository refundRepository;

    public List<NoticeDTO> findAllNoticeByLimit5(){
        List<Notice> notice = noticeRepository.findTop5ByOrderByRegDateDesc();
        List<NoticeDTO> noticeDTOList = new ArrayList<>();
        for (Notice notice1 : notice) {
            NoticeDTO noticeDTO = modelMapper.map(notice1, NoticeDTO.class);
            noticeDTOList.add(noticeDTO);
        }

        return noticeDTOList;
    }

    public List<QnaDTO> findAllQnaByLimit5(){
        List<Qna> qna = qnaRepository.findTop5ByOrderByRegDateDesc();
        List<QnaDTO> qnaDTOList = qna
                .stream()
                .map(qna1 -> {
                    QnaDTO qnaDTO = modelMapper.map(qna1, QnaDTO.class);
                    qnaDTO.setWriter(qna1.getWriter().getId());

                    return qnaDTO;
                })
                .collect(Collectors.toList());

        return qnaDTOList;
    }

}
