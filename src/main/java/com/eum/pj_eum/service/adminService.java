package com.eum.pj_eum.service;

import com.eum.pj_eum.dto.adminRegisterRequest;
import com.eum.pj_eum.dto.loginRequest;
import com.eum.pj_eum.dto.loginResponse;
import com.eum.pj_eum.dto.passwordChangeRequest;
import org.springframework.stereotype.Service;


@Service
public interface adminService {

    /**
     * 관리자 회원가입
     */
    String register(adminRegisterRequest request);

    /**
     * 관리자 로그인
     */
    loginResponse login(loginRequest request);

    /**
     * 비밀번호 변경
     */
    void changePassword(passwordChangeRequest request);

}
