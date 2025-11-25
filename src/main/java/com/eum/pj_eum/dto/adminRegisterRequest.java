package com.eum.pj_eum.dto;

import lombok.Data;

@Data
public class adminRegisterRequest {

    private String adminLoginId;      // 로그인 ID
    private String adminPw;            // 비밀번호
    private String adminName;          // 이름
    private String adminEmail;         // 이메일
    private String adminPhone;         // 전화번호
    private Long sigunguId;            // 시군구 ID (group_id → sigungu_id)
}