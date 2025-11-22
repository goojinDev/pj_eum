package com.eum.pj_eum.dto;


import lombok.Data;

@Data
public class loginRequest {

    private String loginId;    // 로그인 ID (관리자/사용자 공통)
    private String password;   // 비밀번호
    private String loginType;  // 로그인 타입 (ADMIN, USER) - 선택적
}
