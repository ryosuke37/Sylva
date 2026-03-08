package io.github.ryosuke37.sylva.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

abstract class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserByEmailAddress(username);
    }

    abstract UserDetails loadUserByEmailAddress(String emailAddress) throws UsernameNotFoundException;

}
