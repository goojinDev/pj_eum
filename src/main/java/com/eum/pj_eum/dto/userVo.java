package com.eum.pj_eum.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class userVo {

    private Long userId;                 // 사용자 ID (AUTO_INCREMENT)
    private String email;                // 이메일
    private String userName;             // 이름
    private String nickname;             // 닉네임
    private String profileImage;         // 프로필 이미지
    private String providerId;           // 소셜 로그인 제공자 ID
    private String userStatus;           // 계정 상태 (ACTIVE, INACTIVE, WITHDRAWN)
    private Long sigunguId;              // 시군구 ID (group_id → sigungu_id)
    private LocalDate birthDate;         // 생년월일
    private String gender;               // 성별
    private String phone;                // 전화번호
    private String backgroundTheme;      // 배경화면 테마
    private LocalDateTime lastLoginDate; // 마지막 로그인 일시
    private int loginFailCount;          // 로그인 실패 횟수
    private LocalDateTime regDate;       // 등록일
    private LocalDateTime updDate;       // 수정일
}