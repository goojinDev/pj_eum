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

@Slf4j
@Component
@RequiredArgsConstructor
public class oAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final userMapper userMapper;

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

            // JWT 토큰 발급 (추후 구현)
            // String token = jwtTokenProvider.createToken(user.getUserId(), "USER");

            // 프론트엔드로 리다이렉트 (토큰 포함)
            String redirectUrl = String.format("http://localhost:3000/oauth2/redirect?userId=%s&email=%s",
                    user.getUserId(), user.getEmail());

            getRedirectStrategy().sendRedirect(request, response, redirectUrl);
        } else {
            // 오류 처리
            getRedirectStrategy().sendRedirect(request, response, "http://localhost:3000/login?error=true");
        }
    }
}