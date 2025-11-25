package com.eum.pj_eum.service.impl;

import com.eum.pj_eum.config.jwtTokenProvider;
import com.eum.pj_eum.dto.adminRegisterRequest;
import com.eum.pj_eum.dto.adminVO;
import com.eum.pj_eum.dto.loginRequest;
import com.eum.pj_eum.dto.loginResponse;
import com.eum.pj_eum.dto.passwordChangeRequest;
import com.eum.pj_eum.mapper.adminMapper;
import com.eum.pj_eum.service.adminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class adminServiceImpl implements adminService {

    private final adminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;
    private final jwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public Long register(adminRegisterRequest request) {
        // 1. 로그인 ID 중복 확인
        adminVO existingAdmin = adminMapper.findByLoginId(request.getAdminLoginId());
        if (existingAdmin != null) {
            throw new RuntimeException("이미 존재하는 로그인 ID입니다.");
        }

        // 2. 이메일 중복 확인
        adminVO existingEmail = adminMapper.findByEmail(request.getAdminEmail());
        if (existingEmail != null) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        // 3. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getAdminPw());

        // 4. VO 생성 (ID는 AUTO_INCREMENT)
        adminVO admin = new adminVO();
        admin.setAdminLoginId(request.getAdminLoginId());
        admin.setAdminPw(encodedPassword);
        admin.setAdminName(request.getAdminName());
        admin.setAdminEmail(request.getAdminEmail());
        admin.setAdminPhone(request.getAdminPhone());
        admin.setSigunguId(request.getSigunguId());
        admin.setAdminStatus("ACTIVE");
        admin.setLoginFailCount(0);

        // 5. DB 저장 (INSERT 후 adminId 자동 생성)
        adminMapper.insert(admin);

        return admin.getAdminId();
    }

    @Override
    @Transactional
    public loginResponse login(loginRequest request) {
        // 1. 관리자 조회
        adminVO admin = adminMapper.findByLoginId(request.getLoginId());

        if (admin == null) {
            throw new RuntimeException("존재하지 않는 계정입니다.");
        }

        // 2. 계정 상태 확인
        if (!"ACTIVE".equals(admin.getAdminStatus())) {
            throw new RuntimeException("비활성화된 계정입니다.");
        }

        // 3. 비밀번호 확인
        if (!passwordEncoder.matches(request.getPassword(), admin.getAdminPw())) {
            adminMapper.increaseLoginFailCount(admin.getAdminId());
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 4. 최초 로그인 여부 확인
        boolean firstLogin = (admin.getLastLoginDate() == null);

        // 5. 로그인 성공 처리
        adminMapper.updateLastLoginDate(admin.getAdminId());
        adminMapper.resetLoginFailCount(admin.getAdminId());

        // 6. JWT 토큰 생성
        String token = jwtTokenProvider.createToken(admin.getAdminId(), "ADMIN");

        // 7. 응답 생성
        loginResponse response = new loginResponse();
        response.setUserId(admin.getAdminId());
        response.setLoginId(admin.getAdminLoginId());
        response.setName(admin.getAdminName());
        response.setEmail(admin.getAdminEmail());
        response.setSigunguId(admin.getSigunguId());
        response.setFirstLogin(firstLogin);
        response.setToken(token);
        response.setUserType("ADMIN");

        return response;
    }

    @Override
    @Transactional
    public void changePassword(passwordChangeRequest request) {
        // 1. 관리자 조회
        adminVO admin = adminMapper.findById(request.getUserId());

        if (admin == null) {
            throw new RuntimeException("존재하지 않는 계정입니다.");
        }

        // 2. 현재 비밀번호 확인
        if (!passwordEncoder.matches(request.getCurrentPassword(), admin.getAdminPw())) {
            throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");
        }

        // 3. 새 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getNewPassword());

        // 4. 비밀번호 업데이트
        adminMapper.updatePassword(request.getUserId(), encodedPassword);
    }
}