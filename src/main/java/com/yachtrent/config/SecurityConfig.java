package com.yachtrent.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin(formLogin -> formLogin
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginPage("/account/login-page")
                        .loginProcessingUrl("/authenticate")
                        .defaultSuccessUrl("/cabinet")
                )
                .logout(logout -> logout.logoutSuccessUrl("/account/login-page?logout"))
                .authorizeHttpRequests(authorize -> {
                    authorize.anyRequest().permitAll();
                })
                .csrf(AbstractHttpConfigurer::disable).build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

