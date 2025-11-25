package com.eum.pj_eum.dto;

import lombok.Data;

@Data
public class loginResponse {

    private Long userId;             // 사용자/관리자 ID (AUTO_INCREMENT)
    private String loginId;          // 로그인 ID
    private String name;             // 이름
    private String email;            // 이메일
    private Long sigunguId;          // 시군구 ID (group_id → sigungu_id)
    private boolean firstLogin;      // 최초 로그인 여부
    private String token;            // JWT 토큰
    private String userType;         // 사용자 타입 (ADMIN, USER)
    private String profileImage;     // 프로필 이미지 (사용자용)
    private String backgroundTheme;  // 배경화면 테마 (사용자용)
}