package com.eum.pj_eum.dto;

import lombok.Data;

import java.util.Map;

@Data
public class oAuth2UserInfo {
    private String providerId;      // 제공자 ID
    private String provider;        // 제공자 (google, kakao, naver)
    private String email;           // 이메일
    private String name;            // 이름
    private String profileImage;    // 프로필 이미지

    public static oAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
        if ("google".equals(registrationId)) {
            return ofGoogle(attributes);
        }
        // 추후 kakao, naver 추가 가능
        throw new RuntimeException("지원하지 않는 소셜 로그인입니다.");
    }

    private static oAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
        oAuth2UserInfo userInfo = new oAuth2UserInfo();
        userInfo.setProviderId((String) attributes.get("sub"));
        userInfo.setProvider("GOOGLE");
        userInfo.setEmail((String) attributes.get("email"));
        userInfo.setName((String) attributes.get("name"));
        userInfo.setProfileImage((String) attributes.get("picture"));
        return userInfo;
    }
}