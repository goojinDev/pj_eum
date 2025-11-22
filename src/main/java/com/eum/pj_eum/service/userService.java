package com.eum.pj_eum.service;

import com.eum.pj_eum.dto.loginRequest;
import com.eum.pj_eum.dto.loginResponse;
import com.eum.pj_eum.dto.passwordChangeRequest;
import com.eum.pj_eum.dto.userRegisterRequest;
import com.eum.pj_eum.dto.userWithdrawRequest;

public interface userService {

    /**
     * 사용자 회원가입 (일반 + 추가 정보 입력)
     * - userId 없음: 신규 회원가입
     * - userId 있음: 추가 정보 입력
     */
    String register(userRegisterRequest request);

    /**
     * 사용자 로그인
     */
    loginResponse login(loginRequest request);

    /**
     * 비밀번호 변경
     */
    void changePassword(passwordChangeRequest request);

    /**
     * 사용자 정보 수정
     */
    void updateUserInfo(userRegisterRequest request);

    /**
     * 회원 탈퇴
     */
    void withdraw(userWithdrawRequest request);
}