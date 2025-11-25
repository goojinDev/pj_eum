package com.eum.pj_eum.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class userRegisterRequest {
    private Long userId;               // 추가 정보 입력 시 사용 (AUTO_INCREMENT)
    private String email;              // 이메일
    private String userName;           // 이름
    private String nickname;           // 닉네임
    private String providerId;         // 소셜 로그인 제공자 ID
    private Long sigunguId;            // 시군구 ID (group_id → sigungu_id)
    private LocalDate birthDate;       // 생년월일
    private String gender;             // 성별
    private String phone;              // 전화번호
    private String profileImage;       // 프로필 이미지
    private String backgroundTheme;    // 배경화면 테마
}