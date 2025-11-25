package com.eum.pj_eum.dto;

import lombok.Data;

@Data
public class userWithdrawRequest {

    private Long userId;           // 사용자 ID (AUTO_INCREMENT)
    private String withdrawReason; // 탈퇴 사유
    private String password;       // 비밀번호 확인 (일반 로그인 사용자용)
}