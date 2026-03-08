package io.github.ryosuke37.sylva.service;

import io.github.ryosuke37.sylva.repository.entity.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AccountUserDetails implements UserDetails {
    @Getter
    private final UserEntity userEntity;

    private final Collection<GrantedAuthority> authorities;

    AccountUserDetails(UserEntity userEntity, Collection<GrantedAuthority> authorities) {
        this.userEntity = userEntity;
        this.authorities = authorities;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getHashedPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getEmailAddress();
    }
}
