package io.github.ryosuke37.sylva.config;

import io.github.ryosuke37.sylva.common.constant.Routes;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    WebSecurityConfig(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ){
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        System.out.println("SecurityConfig loaded");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers(
                        Routes.ROOT,
                        Routes.SIGNUP,
                        Routes.LOGIN,
                        Routes.ERROR //エラーページをログインフィルターの対象外とする。
                ).permitAll()
                .anyRequest().authenticated()
        ).formLogin(login -> login
                .defaultSuccessUrl(Routes.ROOT, true)
                .loginPage(Routes.LOGIN)
                .usernameParameter("emailAddress")
                .passwordParameter("rawPassword")
                .permitAll()
        ).logout((logout)->logout
                .logoutUrl(Routes.LOGOUT)
                .logoutSuccessUrl(Routes.ROOT)
        );
        return http.build();
    }

    @Autowired
    void configureAuthenticationManager(AuthenticationManagerBuilder auth) throws  Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}
