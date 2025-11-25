package com.eum.pj_eum.service;

import com.eum.pj_eum.dto.oAuth2UserInfo;
import com.eum.pj_eum.dto.userVo;
import com.eum.pj_eum.mapper.userMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class customOAuth2UserService extends DefaultOAuth2UserService {

    private final userMapper userMapper;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1. OAuth2 사용자 정보 가져오기
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 2. 제공자 정보 추출
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        log.info("OAuth2 로그인 - Provider: {}", registrationId);

        // 3. OAuth2UserInfo 객체 생성
        oAuth2UserInfo userInfo = oAuth2UserInfo.of(registrationId, oAuth2User.getAttributes());

        // 4. 사용자 정보 조회 또는 생성
        userVo user = saveOrUpdate(userInfo);

        // 5. OAuth2User 반환
        return new DefaultOAuth2User(
                Collections.singleton(() -> "ROLE_USER"),
                oAuth2User.getAttributes(),
                userNameAttributeName
        );
    }

    private userVo saveOrUpdate(oAuth2UserInfo userInfo) {
        // 1. 이메일로 기존 사용자 조회
        userVo existingUser = userMapper.findByEmail(userInfo.getEmail());

        if (existingUser != null) {
            // 2-1. 기존 사용자가 있으면 프로필 이미지 업데이트
            log.info("기존 사용자 로그인: {}", userInfo.getEmail());

            if (userInfo.getProfileImage() != null) {
                userMapper.updateProfileImage(existingUser.getUserId(), userInfo.getProfileImage());
            }

            return existingUser;
        } else {
            // 2-2. 신규 사용자 등록 (AUTO_INCREMENT)
            log.info("신규 사용자 등록: {}", userInfo.getEmail());

            userVo newUser = new userVo();
            newUser.setEmail(userInfo.getEmail());
            newUser.setUserName(userInfo.getName());
            newUser.setNickname(userInfo.getName()); // 기본값으로 이름 사용
            newUser.setProfileImage(userInfo.getProfileImage());
            newUser.setProviderId(userInfo.getProviderId());
            newUser.setUserStatus("ACTIVE");
            newUser.setBackgroundTheme("DEFAULT");
            newUser.setLoginFailCount(0);

            userMapper.insert(newUser);

            return newUser;
        }
    }
}