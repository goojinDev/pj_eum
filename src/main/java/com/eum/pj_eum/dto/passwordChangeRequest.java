package com.eum.pj_eum.dto;


import lombok.Data;

@Data
public class passwordChangeRequest {

    private String userId;           // 사용자/관리자 ID
    private String currentPassword;  // 현재 비밀번호
    private String newPassword;      // 새 비밀번호
}
