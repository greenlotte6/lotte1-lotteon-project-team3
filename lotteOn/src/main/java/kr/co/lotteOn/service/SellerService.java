package kr.co.lotteOn.service;

import kr.co.lotteOn.dto.SellerDTO;
import kr.co.lotteOn.entity.Seller;
import kr.co.lotteOn.repository.SellerRepository;
import kr.co.lotteOn.repository.SellerProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    //아이디 존재 여부 확인
    public boolean existsBySellerId(String sellerId) {
        return sellerRepository.existsBySellerId(sellerId);
    }

    public boolean isCompanyNameExist(String companyName) {
        return sellerRepository.existsByCompanyName(companyName);
    }

    public boolean isBusinessNoExist(String businessNo) {
        return sellerRepository.existsByBusinessNo(businessNo);
    }

    public boolean isCommunicationNoExist(String communicationNo) {
        return sellerRepository.existsByCommunicationNo(communicationNo);
    }

    public boolean isSHpExist(String hp) {
        return sellerRepository.existsByHp(hp);
    }

    public boolean isFaxExist(String fax) {
        return sellerRepository.existsByFax(fax);
    }

    //판매자 등록
    public void register(SellerDTO sellerDTO) {
        //비밀번호 암호화
        String encodedPass= passwordEncoder.encode(sellerDTO.getPassword());
        sellerDTO.setPassword(encodedPass);

        //엔티티 변환
        Seller seller = modelMapper.map(sellerDTO, Seller.class);

        //저장
        sellerRepository.save(seller);
    }

    public List<SellerProjection> getSellerList(){
        return sellerRepository.findAllBy();
    }
}
