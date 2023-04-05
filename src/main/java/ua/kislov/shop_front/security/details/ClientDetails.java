package ua.kislov.shop_front.security.details;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.kislov.shop_front.models.ShopClient;

import java.util.Collection;
import java.util.Collections;

public class ClientDetails implements UserDetails {

    private final ShopClient shopClient;

    public ClientDetails(ShopClient shopClient) {
        this.shopClient = shopClient;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.shopClient.getRole()));
    }

    @Override
    public String getPassword() {
        return this.shopClient.getPassword();
    }

    @Override
    public String getUsername() {
        return this.shopClient.getUsername();
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
