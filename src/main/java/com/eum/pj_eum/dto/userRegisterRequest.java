package com.eum.pj_eum.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class userRegisterRequest {
    private String userId;             // 추가 정보 입력 시 사용
    private String email;              // 이메일
    private String userPw;             // 비밀번호 (일반 회원가입용)
    private String userName;           // 이름
    private String nickname;           // 닉네임
    private String loginType;          // 로그인 타입 (LOCAL, GOOGLE, KAKAO, NAVER)
    private String providerId;         // 소셜 로그인 제공자 ID
    private String groupId;            // 소속 그룹 ID
    private LocalDate birthDate;       // 생년월일
    private String gender;             // 성별
    private String phone;              // 전화번호
    private String profileImage;       // 프로필 이미지
    private String backgroundTheme;    // 배경화면 테마
}