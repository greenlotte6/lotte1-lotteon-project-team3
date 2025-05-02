package kr.co.lotteOn.service;

import com.querydsl.core.Tuple;
import jakarta.transaction.Transactional;
import kr.co.lotteOn.dto.coupon.CouponDTO;
import kr.co.lotteOn.dto.MemberDTO;
import kr.co.lotteOn.dto.coupon.CouponPageRequestDTO;
import kr.co.lotteOn.dto.coupon.CouponPageResponseDTO;
import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponDTO;
import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponPageRequestDTO;
import kr.co.lotteOn.dto.issuedCoupon.IssuedCouponPageResponseDTO;
import kr.co.lotteOn.dto.point.PointDTO;
import kr.co.lotteOn.dto.point.PointPageRequestDTO;
import kr.co.lotteOn.dto.point.PointPageResponseDTO;
import kr.co.lotteOn.entity.Coupon;
import kr.co.lotteOn.entity.IssuedCoupon;
import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.entity.Point;
import kr.co.lotteOn.repository.CouponRepository;
import kr.co.lotteOn.repository.IssuedCouponRepository;
import kr.co.lotteOn.repository.MemberRepository;
import kr.co.lotteOn.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminMemberService {

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final PointRepository pointRepository;
    private final ModelMapper modelMapper;
    private final IssuedCouponRepository issuedCouponRepository;

    public Page<MemberDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return memberRepository.findAll(pageable)
                .map(member -> modelMapper.map(member, MemberDTO.class));
    }

    public MemberDTO findById(String id) {
        Optional<Member> optMember = memberRepository.findById(id);

        if (optMember.isPresent()) {
            return modelMapper.map(optMember.get(), MemberDTO.class);
        }

        return null;
    }

    public MemberDTO modify(MemberDTO memberDTO) {
        Optional<Member> optional = memberRepository.findById(memberDTO.getId());

        if (optional.isPresent()) {
            Member member = optional.get();

            // 필요한 값만 수정
            member.setName(memberDTO.getName());
            member.setEmail(memberDTO.getEmail());
            member.setHp(memberDTO.getHp());
            member.setRating(memberDTO.getRating());
            member.setGender(memberDTO.getGender());
            member.setZip(memberDTO.getZip());
            member.setAddr1(memberDTO.getAddr1());
            member.setAddr2(memberDTO.getAddr2());
            member.setAnother(memberDTO.getAnother());

            return modelMapper.map(memberRepository.save(member), MemberDTO.class);
        }

        return null;
    }

    public Page<MemberDTO> searchMembers(String type, String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Member> memberPage;

        if ("id".equals(type)) {
            memberPage = memberRepository.findByIdContaining(keyword, pageable);
        } else if ("name".equals(type)) {
            memberPage = memberRepository.findByNameContaining(keyword, pageable);
        } else if ("email".equals(type)) {
            memberPage = memberRepository.findByEmailContaining(keyword, pageable);
        } else if ("hp".equals(type)) {
            memberPage = memberRepository.findByHpContaining(keyword, pageable);
        } else {
            memberPage = Page.empty(pageable); // 빈 페이지 처리
        }

        return memberPage.map(member -> modelMapper.map(member, MemberDTO.class));
    }

    public void updateMemberStatus(String memberId, String newStatus) {
        Optional<Member> memberOpt = memberRepository.findById(memberId);
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            member.setStatus(newStatus); // '중지'로 상태 변경
            memberRepository.save(member); // 상태 변경 후 저장
        }
    }

    /* ******************회원관리 끝******************************/

    //포인트 적립내역 - 리스트
    public PointPageResponseDTO pointList(PointPageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.getPageable("pointNo");

        Page<Tuple> pagePoints = pointRepository.findAllPoint(pageable);

        List<PointDTO> pointDTOList = pagePoints
                .getContent()
                .stream()
                .map(tuple -> {
                    Point point = tuple.get(0, Point.class);
                    String id = tuple.get(1, String.class);
                    String name = tuple.get(2, String.class);

                    PointDTO pointDTO = modelMapper.map(point, PointDTO.class);
                    pointDTO.setMemberId(id);
                    pointDTO.setName(name);

                    return pointDTO;
                }).toList();

        int total = (int) pagePoints.getTotalElements();

        return PointPageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(pointDTOList)
                .total(total)
                .build();

    }

    //포인트 적립내역 - 검색
    public PointPageResponseDTO pointListSearch(PointPageRequestDTO pageRequestDTO){

        Pageable pageable = pageRequestDTO.getPageable("pointNo");
        Page<Tuple> pagePoints = pointRepository.searchPoint(pageRequestDTO, pageable);

        List<PointDTO> pointDTOList = pagePoints
                .getContent()
                .stream()
                .map(tuple -> {
                    Point point = tuple.get(0, Point.class);
                    String name = tuple.get(1, String.class);

                    PointDTO pointDTO = modelMapper.map(point, PointDTO.class);
                    pointDTO.setName(name);

                    return pointDTO;
                }).toList();

        int total = (int) pagePoints.getTotalElements();

        return PointPageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(pointDTOList)
                .total(total)
                .build();

    }

    /* ******************포인트관리 끝******************************/

    private String generateCouponCode(String couponType, int benefit) {
        String prefix;

        switch (couponType.toLowerCase()) {
            case "each":
                prefix = "DIS" + benefit + "-PERCENT";
                break;
            case "order":
                prefix = "DIS" + benefit + "-AMOUNT";
                break;
            case "free":
                prefix = "FREESHIP";
                break;
            default:
                prefix = "GENERIC";
        }

        String random = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return prefix + "-" + random;
    }

    //쿠폰목록 - 등록
    public int registerCoupon(CouponDTO couponDTO){

        //benefit에서 숫자 추출
        int benefitValue = 0;
        // free 타입이 아닐 때만 숫자 추출
        if (!"free".equalsIgnoreCase(couponDTO.getCouponType())) {
            benefitValue = couponDTO.extractNumber();
        }

        Coupon coupon = modelMapper.map(couponDTO, Coupon.class);
        String generatedCode = generateCouponCode(couponDTO.getCouponType(), benefitValue);
        coupon.setCouponCode(generatedCode);

        Coupon savedCoupon = couponRepository.save(coupon);

        return savedCoupon.getIssuedNo();

    }

    //쿠폰목록 - 출력(리스트)
    public CouponPageResponseDTO couponFindAll(CouponPageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("issuedNo");
        Page<Coupon> pageCoupons = couponRepository.findAll(pageable);

        List<CouponDTO> couponList = pageCoupons
                .getContent()
                .stream()
                .map(coupon -> modelMapper.map(coupon, CouponDTO.class)).toList();

        int total = (int) pageCoupons.getTotalElements();

        return CouponPageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(couponList)
                .total(total)
                .build();

    }

    //쿠폰목록 - 출력(검색)
    public CouponPageResponseDTO couponSearchAll(CouponPageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("issuedNo");
        Page<Tuple> pageCoupons = couponRepository.searchCoupons(pageRequestDTO, pageable);

        List<CouponDTO> couponDTOList = pageCoupons.getContent().stream().map(tuple -> {
            Coupon coupon = tuple.get(0, Coupon.class);
            String companyName = tuple.get(1, String.class);

            CouponDTO couponDTO = modelMapper.map(coupon, CouponDTO.class);
            couponDTO.setCompanyName(companyName);

            return couponDTO;
        }).toList();

        int total = (int) pageCoupons.getTotalElements();

        return CouponPageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(couponDTOList)
                .total(total)
                .build();
    }


    //쿠폰목록 - 조회(번호)
    public CouponDTO couponFindById(String couponCode) {
        Optional<Coupon> optCoupon = couponRepository.findByCouponCode(couponCode);
        if (optCoupon.isPresent()) {
            Coupon coupon = optCoupon.get();
            CouponDTO couponDTO = modelMapper.map(coupon, CouponDTO.class);
            return couponDTO;
        }

        return null;
    }

    //쿠폰목록 - 종료
    @Transactional
    public void endCoupon(String couponCode) {
        Optional<Coupon> coupon = couponRepository.findByCouponCode(couponCode);
        if (coupon.isPresent()) {
            Coupon coupon1 = coupon.get();
            coupon1.setStatus("발급 종료");
        }
    }

    //쿠폰발급현황 - 출력
    public IssuedCouponPageResponseDTO issuedCouponFindAll(IssuedCouponPageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("issuedNo");
        Page<Tuple> pageIssuedCoupons = issuedCouponRepository.findAllForList(pageable);

        List<IssuedCouponDTO> issuedCouponDTOList = pageIssuedCoupons
                .getContent()
                .stream()
                .map(tuple -> {
                    IssuedCoupon issuedCoupon = tuple.get(0, IssuedCoupon.class);
                    Coupon coupon = tuple.get(1, Coupon.class);
                    String memberId = tuple.get(2, String.class);


                    IssuedCouponDTO issuedCouponDTO = modelMapper.map(issuedCoupon, IssuedCouponDTO.class);
                    issuedCouponDTO.setCouponCode(coupon.getCouponCode());
                    issuedCouponDTO.setCouponType(coupon.getCouponType());
                    issuedCouponDTO.setCouponName(coupon.getCouponName());
                    issuedCouponDTO.setBenefit(coupon.getBenefit());
                    issuedCouponDTO.setCompanyName(coupon.getCompanyName());
                    issuedCouponDTO.setStartDate(coupon.getStartDate());
                    issuedCouponDTO.setEndDate(coupon.getEndDate());
                    issuedCouponDTO.setEtc(coupon.getEtc());

                    issuedCouponDTO.setMemberId(memberId);

                    return issuedCouponDTO;


                }).toList();

        int total = (int) pageIssuedCoupons.getTotalElements();

        return IssuedCouponPageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(issuedCouponDTOList)
                .total(total)
                .build();

    }


    //쿠폰발급현황 - 출력(검색)
    public IssuedCouponPageResponseDTO issuedCouponSearchAll(IssuedCouponPageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("issuedNo");
        Page<Tuple> pageIssuedCoupons = issuedCouponRepository.searchAll(pageRequestDTO, pageable);

        List<IssuedCouponDTO> issuedCouponDTOList = pageIssuedCoupons
                .getContent()
                .stream()
                .map(tuple -> {
                    IssuedCoupon issuedCoupon = tuple.get(0, IssuedCoupon.class);
                    Coupon coupon = tuple.get(1, Coupon.class);
                    String memberId = tuple.get(2, String.class);


                    IssuedCouponDTO issuedCouponDTO = modelMapper.map(issuedCoupon, IssuedCouponDTO.class);
                    issuedCouponDTO.setCouponCode(coupon.getCouponCode());
                    issuedCouponDTO.setCouponType(coupon.getCouponType());
                    issuedCouponDTO.setCouponName(coupon.getCouponName());
                    issuedCouponDTO.setBenefit(coupon.getBenefit());
                    issuedCouponDTO.setCompanyName(coupon.getCompanyName());
                    issuedCouponDTO.setStartDate(coupon.getStartDate());
                    issuedCouponDTO.setEndDate(coupon.getEndDate());
                    issuedCouponDTO.setEtc(coupon.getEtc());

                    issuedCouponDTO.setMemberId(memberId);

                    return issuedCouponDTO;


                }).toList();

        int total = (int) pageIssuedCoupons.getTotalElements();

        return IssuedCouponPageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(issuedCouponDTOList)
                .total(total)
                .build();

    }


    //쿠폰발급현황 - 조회


    //쿠폰발급현황 - 종료
    @Transactional
    public void endIssuedCoupon(int issuedNo) {
        Optional<IssuedCoupon> optIssuedCoupon = issuedCouponRepository.findById(issuedNo);
        if (optIssuedCoupon.isPresent()) {
            IssuedCoupon issuedCoupon = optIssuedCoupon.get();
            issuedCoupon.setStatus("사용 불가");
        }
    }

    /* ******************쿠폰관리 끝******************************/
}
