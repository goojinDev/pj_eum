package com.eum.pj_eum.config;

import com.eum.pj_eum.service.customOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class securityConfig {

    private final customOAuth2UserService customOAuth2UserService;
    private final oAuth2SuccessHandler oAuth2SuccessHandler;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CORS 설정 적용
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                // CSRF 비활성화 (REST API이므로)
                .csrf(csrf -> csrf.disable())
                // 요청 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // API 엔드포인트 모두 허용
                        .requestMatchers("/admin/**", "/user/**", "/chat/**", "/eumChat/**", "/home/**").permitAll()
                        // Swagger 허용
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
                        // OAuth2 로그인 허용
                        .requestMatchers("/login/oauth2/**", "/oauth2/**").permitAll()
                        // 그 외 요청은 인증 필요
                        .anyRequest().authenticated()
                )
                // Form 로그인 비활성화
                .formLogin(form -> form.disable())
                // HTTP Basic 비활성화
                .httpBasic(basic -> basic.disable())
                // OAuth2 로그인 설정
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(oAuth2SuccessHandler)
                );

        return http.build();
    }
}