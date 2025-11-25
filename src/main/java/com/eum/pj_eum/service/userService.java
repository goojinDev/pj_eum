package com.eum.pj_eum.service;

import com.eum.pj_eum.dto.loginRequest;
import com.eum.pj_eum.dto.loginResponse;
import com.eum.pj_eum.dto.userRegisterRequest;
import com.eum.pj_eum.dto.userWithdrawRequest;

public interface userService {

    /**
     * 사용자 로그인 (Google OAuth2만 사용)
     */
    loginResponse login(loginRequest request);

    /**
     * 사용자 정보 수정
     */
    void updateUserInfo(userRegisterRequest request);

    /**
     * 회원 탈퇴
     */
    void withdraw(userWithdrawRequest request);
}