package kr.co.lotteOn.security;

import kr.co.lotteOn.entity.Member;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class MyUserDetails implements UserDetails, OAuth2User {

    private Member member;

    // 구글 사용자 정보 속성
    private Map<String, Object> attributes;
    private String accessToken;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //권한 목록 생성
        List<GrantedAuthority> authorities = new ArrayList<>();
        //계정 권한 앞에 접두어 ROLE_ 붙여야 됨
        authorities.add(new SimpleGrantedAuthority("ROLE_" + member.getRole()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return member.getId();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public String getName() {
        return member.getId();
    }
}