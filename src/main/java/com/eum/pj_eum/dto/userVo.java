package com.eum.pj_eum.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class userVo {

    private String userId;               // 사용자 ID
    private String email;                // 이메일
    private String userPw;               // 비밀번호
    private String userName;             // 이름
    private String nickname;             // 닉네임
    private String profileImage;         // 프로필 이미지
    private String loginType;            // 로그인 타입 (LOCAL, GOOGLE, KAKAO, NAVER)
    private String providerId;           // 소셜 로그인 제공자 ID
    private String userRole;             // 사용자 역할 (USER, ADMIN)
    private String userStatus;           // 계정 상태 (ACTIVE, INACTIVE, WITHDRAWN)
    private String groupId;              // 소속 그룹 ID
    private LocalDate birthDate;         // 생년월일
    private String gender;               // 성별
    private String phone;                // 전화번호
    private String emailVerified;        // 이메일 인증 여부 (Y/N)
    private String backgroundTheme;      // 배경화면 테마
    private LocalDateTime lastLoginDate; // 마지막 로그인 일시
    private int loginFailCount;          // 로그인 실패 횟수
    private LocalDateTime regDate;       // 등록일
    private LocalDateTime updDate;       // 수정일
}
