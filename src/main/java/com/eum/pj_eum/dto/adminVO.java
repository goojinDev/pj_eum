package com.eum.pj_eum.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class adminVO {

    private String adminId;              // 관리자 ID
    private String adminLoginId;         // 로그인 ID
    private String adminPw;              // 비밀번호
    private String adminName;            // 이름
    private String adminEmail;           // 이메일
    private String adminPhone;           // 전화번호
    private String adminStatus;          // 계정 상태 (ACTIVE, INACTIVE)
    private String groupId;              // 소속 그룹 ID
    private LocalDateTime lastLoginDate; // 마지막 로그인 일시
    private int loginFailCount;          // 로그인 실패 횟수
    private LocalDateTime regDate;       // 등록일
    private LocalDateTime updDate;       // 수정일
}
