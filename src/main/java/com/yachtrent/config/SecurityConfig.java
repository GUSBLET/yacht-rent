package com.yachtrent.config;


import com.yachtrent.main.profile.ProfileController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @Order(0)
    SecurityFilterChain resources(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/images/**", "/js/**", "/css/**", "/**.css", "/**.js")
                .authorizeHttpRequests(c -> c.anyRequest().permitAll())
                .securityContext(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .requestCache(RequestCacheConfigurer::disable)
                .build();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin(formLogin -> formLogin
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginPage("/account/login-page")
                        .loginProcessingUrl("/authenticate")
                        .defaultSuccessUrl("/cabinet")
                        .permitAll()
                )
                .logout(logout -> logout.logoutSuccessUrl("/account/login-page"))
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(
                            "/home**",
                            "/home/search",
                            "/home/filter",
                            "/yacht/info",
                            "/account/registration-page",
                            "/account/sign-up",
                            "/account/change-password",
                            "/account/password/**",
                            "/account/verify/**",
                            "/account/login-page**"
                    ).permitAll();
                    authorize
                            .requestMatchers(
                                    "/cabinet",
                                    "/cabinet/**",
                                    "/order/**",
                                    "/account/edit-account**",
                                    "/order**",
                                    "/account/change-email/**",
                                    "/cabinet/clear_profile"
                            ).authenticated()
                            .requestMatchers(
                                    "/cabinet/profile-yacht/**",
                                    "/yacht/add-yacht**",
                                    "/yacht/update",
                                    "/yacht/delete-yacht"
                            ).hasAnyAuthority("YACHT_OWNER")
                            .requestMatchers("/cabinet/all_user/**").hasAnyAuthority("ADMIN")
                            .requestMatchers("/cabinet/all_orders/**").hasAnyAuthority("MANAGER", "MODERATOR", "ADMIN");
                })
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
