package kr.co.lotteOn.security;

import kr.co.lotteOn.entity.Seller;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@Builder
public class SellerUserDetails implements UserDetails {
    private Seller seller;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(()->"ROLE_SELLER");
    }

    @Override
    public String getPassword() {
        return seller.getPassword();
    }

    @Override
    public String getUsername() {
        return seller.getSellerId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
