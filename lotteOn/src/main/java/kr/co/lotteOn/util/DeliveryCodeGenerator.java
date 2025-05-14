package kr.co.lotteOn.util;

import kr.co.lotteOn.dto.delivery.DeliveryDTO;
import kr.co.lotteOn.entity.Delivery;
import kr.co.lotteOn.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class DeliveryCodeGenerator {

    private final DeliveryRepository deliveryRepository;
    private final ModelMapper modelMapper;

    // 송장번호 생성 메서드
    public String generateWaybillNumber() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        int random = new Random().nextInt(9000) + 1000; // 1000 ~ 9999
        return random + timestamp; // 예: 202505141523441234
    }

    // 예시: 배송 생성 시 자동 부여
    public Delivery createDelivery(DeliveryDTO deliveryDTO) {
        String waybill = generateWaybillNumber();

        Delivery delivery = modelMapper.map(deliveryDTO, Delivery.class);
        delivery.setWaybill(waybill);

        return deliveryRepository.save(delivery);
    }


}
