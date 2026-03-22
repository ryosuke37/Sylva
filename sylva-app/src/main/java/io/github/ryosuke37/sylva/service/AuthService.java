package io.github.ryosuke37.sylva.service;

import io.github.ryosuke37.sylva.common.enums.Language;
import io.github.ryosuke37.sylva.mapper.UserMapper;
import io.github.ryosuke37.sylva.controller.dto.UserDto;
import io.github.ryosuke37.sylva.controller.form.SignupForm;
import io.github.ryosuke37.sylva.repository.UserRepository;
import io.github.ryosuke37.sylva.repository.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService extends MyUserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final UserMapper userMapper;

    @Autowired
    AuthService(
            UserRepository userRepository,
            BCryptPasswordEncoder bcryptPasswordEncoder,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.encoder = bcryptPasswordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    UserDetails loadUserByEmailAddress(String emailAddress) throws UsernameNotFoundException {
        try {
            UserEntity userEntity = userRepository.findByEmailAddress(emailAddress).getFirst();
            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
            return new AccountUserDetails(userEntity, authorities);
        } catch (NullPointerException e) {
            throw new UsernameNotFoundException("{user.not.found}");
        }
    }

    public boolean existsByEmail(String emailAddress) {
        return !userRepository.findByEmailAddress(emailAddress).isEmpty();
    }

    public boolean existsByHandle(String handle) {
        return !userRepository.findByHandle(handle).isEmpty();
    }

    public void signUp(SignupForm signUpForm) {
        String hashedPassword = encoder.encode(signUpForm.getRawPassword());
        UserEntity userEntity = new UserEntity()
                .setHandle(signUpForm.getHandle())
                .setNickname(signUpForm.getNickname())
                .setEmailAddress(signUpForm.getEmailAddress())
                .setHashedPassword(hashedPassword)
                .setLanguage(Language.Japanese);
        userRepository.save(userEntity);
    }

    public List<UserEntity> singIn() {
        return userRepository.findAll();
    }

    public UserDto getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            UserEntity userEntity = ((AccountUserDetails)userDetails).getUserEntity();
            return userMapper.toDto(userEntity);
        }
        return null;
    }
}