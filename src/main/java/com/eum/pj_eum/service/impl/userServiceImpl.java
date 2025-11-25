package com.eum.pj_eum.service.impl;

import com.eum.pj_eum.config.jwtTokenProvider;
import com.eum.pj_eum.dto.loginRequest;
import com.eum.pj_eum.dto.loginResponse;
import com.eum.pj_eum.dto.userRegisterRequest;
import com.eum.pj_eum.dto.userVo;
import com.eum.pj_eum.dto.userWithdrawRequest;
import com.eum.pj_eum.mapper.userMapper;
import com.eum.pj_eum.service.userService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class userServiceImpl implements userService {

    private final userMapper userMapper;
    private final jwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public loginResponse login(loginRequest request) {
        // 1. 사용자 조회 (이메일로 조회)
        userVo user = userMapper.findByEmail(request.getLoginId());

        if (user == null) {
            throw new RuntimeException("존재하지 않는 계정입니다.");
        }

        // 2. 계정 상태 확인
        if (!"ACTIVE".equals(user.getUserStatus())) {
            throw new RuntimeException("비활성화된 계정입니다.");
        }

        // 3. 최초 로그인 여부 확인
        boolean firstLogin = (user.getLastLoginDate() == null);

        // 4. 로그인 성공 처리
        userMapper.updateLastLoginDate(user.getUserId());
        userMapper.resetLoginFailCount(user.getUserId());

        // 5. JWT 토큰 생성
        String token = jwtTokenProvider.createToken(user.getUserId(), "USER");

        // 6. 응답 생성
        loginResponse response = new loginResponse();
        response.setUserId(user.getUserId());
        response.setLoginId(user.getEmail());
        response.setName(user.getUserName());
        response.setEmail(user.getEmail());
        response.setSigunguId(user.getSigunguId());
        response.setFirstLogin(firstLogin);
        response.setToken(token);
        response.setUserType("USER");
        response.setProfileImage(user.getProfileImage());
        response.setBackgroundTheme(user.getBackgroundTheme());

        return response;
    }

    @Override
    @Transactional
    public void updateUserInfo(userRegisterRequest request) {
        // 1. 사용자 조회
        if (request.getUserId() == null) {
            throw new RuntimeException("사용자 ID가 필요합니다.");
        }

        userVo user = userMapper.findById(request.getUserId());

        if (user == null) {
            throw new RuntimeException("존재하지 않는 계정입니다.");
        }

        // 2. 사용자 정보 업데이트
        userMapper.updateUserInfo(request);
    }

    @Override
    @Transactional
    public void withdraw(userWithdrawRequest request) {
        // 1. 사용자 조회
        userVo user = userMapper.findById(request.getUserId());

        if (user == null) {
            throw new RuntimeException("존재하지 않는 계정입니다.");
        }

        // 2. 탈퇴 처리 (상태 변경)
        userMapper.updateStatus(request.getUserId(), "WITHDRAWN");
    }
}