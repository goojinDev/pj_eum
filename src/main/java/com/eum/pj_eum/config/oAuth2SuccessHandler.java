package com.eum.pj_eum.config;

import com.eum.pj_eum.dto.userVo;
import com.eum.pj_eum.mapper.userMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class oAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final userMapper userMapper;
    private final jwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        log.info("OAuth2 로그인 성공: {}", email);

        // 사용자 정보 조회
        userVo user = userMapper.findByEmail(email);

        if (user != null) {
            // 마지막 로그인 시간 업데이트
            userMapper.updateLastLoginDate(user.getUserId());

            // JWT 토큰 발급
            String token = jwtTokenProvider.createToken(user.getUserId(), "USER");

            // 프론트엔드로 리다이렉트 (토큰 포함)
            String redirectUrl = String.format(
                    "http://localhost:3000/oauth2/redirect?token=%s&userId=%s&email=%s&userName=%s",
                    URLEncoder.encode(token, StandardCharsets.UTF_8),
                    user.getUserId(),
                    URLEncoder.encode(user.getEmail(), StandardCharsets.UTF_8),
                    URLEncoder.encode(user.getUserName(), StandardCharsets.UTF_8)
            );

            log.info("OAuth2 로그인 성공 - 리다이렉트: userId={}, email={}", user.getUserId(), user.getEmail());
            getRedirectStrategy().sendRedirect(request, response, redirectUrl);
        } else {
            // 오류 처리
            log.error("OAuth2 로그인 실패 - 사용자 정보 없음: email={}", email);
            getRedirectStrategy().sendRedirect(request, response, "http://localhost:3000/login?error=true");
        }
    }
}