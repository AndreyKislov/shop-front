package ua.kislov.shop_front.security.details;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.kislov.shop_front.models.SecurityShopClient;

import java.util.Collection;
import java.util.Collections;

public record ClientDetails(SecurityShopClient securityShopClient) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.securityShopClient.getRole()));
    }

    @Override
    public String getPassword() {
        return this.securityShopClient.getPassword();
    }

    @Override
    public String getUsername() {
        return this.securityShopClient.getUsername();
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

    @Override
    public String toString() {
        return "ClientDetails{" +
                "securityShopClient=" + securityShopClient +
                '}';
    }
}
