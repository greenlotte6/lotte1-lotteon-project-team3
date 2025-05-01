package kr.co.lotteOn.oauth2;



import kr.co.lotteOn.entity.Member;
import kr.co.lotteOn.repository.MemberRepository;
import kr.co.lotteOn.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // OAuth 인증 업체(구글, 네이버, 카카오...) 유저 정보 객체 반환
        log.info("userRequest : {}", userRequest);

        String accessToken = userRequest.getAccessToken().getTokenValue();
        log.info("accessToken : {}", accessToken);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        log.info("provider : {}", provider);

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("oAuth2User : {}", oAuth2User);

        Map<String, Object> attrs = oAuth2User.getAttributes();

        String email = null;
        String name = null;

        // Naver 응답 처리
        if (attrs.get("response") != null) {
            Map<String, Object> response = (Map<String, Object>) attrs.get("response");
            email = (String) response.get("email");
            name = (String) response.get("name");
        }
        // Kakao 응답 처리
        else if (attrs.get("kakao_account") != null) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attrs.get("kakao_account");
            email = (String) kakaoAccount.get("email");

            // profile 정보가 별도로 들어있음
            if (kakaoAccount.get("profile") != null) {
                Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                name = (String) profile.get("nickname");
            }
        }
        // Google 응답 처리
        else {
            email = (String) attrs.get("email");
            name = (String) attrs.get("name");
        }

        // UID 추출
        String id = null;
        if (email != null && email.contains("@")) {
            id = email.substring(0, email.lastIndexOf("@"));
        } else {
            throw new IllegalArgumentException("이메일 정보가 없어서 uid 생성 불가");
        }

        // 회원 테이블에서 사용자 확인
        Optional<Member> optUser = memberRepository.findById(id);

        Member member = null;

        if(optUser.isPresent()){
            member = optUser.get();
        }else{
            // 회원이 존재하지 않으면 OAuth 회원 정보 저장
            member = Member.builder()
                    .id(id)
                    .email(email)
                    .provider(provider)
                    .name(name)
                    .role("MEMBER")
                    .build();

            memberRepository.save(member);
        }

        return MyUserDetails.builder()
                .member(member)
                .attributes(attrs)
                .accessToken(accessToken)
                .build();
    }
}
