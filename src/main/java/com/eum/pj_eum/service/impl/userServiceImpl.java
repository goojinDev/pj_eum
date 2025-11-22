package com.eum.pj_eum.service.impl;

import com.eum.pj_eum.dto.loginRequest;
import com.eum.pj_eum.dto.loginResponse;
import com.eum.pj_eum.dto.passwordChangeRequest;
import com.eum.pj_eum.dto.userRegisterRequest;
import com.eum.pj_eum.dto.userVo;
import com.eum.pj_eum.dto.userWithdrawRequest;
import com.eum.pj_eum.mapper.userMapper;
import com.eum.pj_eum.service.userService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class userServiceImpl implements userService {

    private final userMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String register(userRegisterRequest request) {

        // 1. userId가 있으면 → 추가 정보 입력 (UPDATE)
        if (request.getUserId() != null && !request.getUserId().isEmpty()) {
            userVo existingUser = userMapper.findById(request.getUserId());

            if (existingUser == null) {
                throw new RuntimeException("존재하지 않는 계정입니다.");
            }

            // 추가 정보 업데이트
            userMapper.updateAdditionalInfo(
                    request.getUserId(),
                    request.getNickname(),
                    request.getGroupId(),
                    request.getBirthDate(),
                    request.getGender(),
                    request.getPhone(),
                    request.getBackgroundTheme()
            );

            return request.getUserId();
        }

        // 2. userId가 없으면 → 신규 회원가입 (INSERT)

        // 이메일 중복 확인
        userVo existingUser = userMapper.findByEmail(request.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        // 사용자 ID 생성 (UUID)
        String userId = "USER-" + UUID.randomUUID().toString();

        // VO 생성
        userVo user = new userVo();
        user.setUserId(userId);
        user.setEmail(request.getEmail());
        user.setUserName(request.getUserName());
        user.setNickname(request.getNickname());
        user.setLoginType(request.getLoginType() != null ? request.getLoginType() : "LOCAL");
        user.setProviderId(request.getProviderId());
        user.setUserRole("USER");
        user.setUserStatus("ACTIVE");
        user.setGroupId(request.getGroupId());
        user.setBirthDate(request.getBirthDate());
        user.setGender(request.getGender());
        user.setPhone(request.getPhone());
        user.setProfileImage(request.getProfileImage());
        user.setBackgroundTheme(request.getBackgroundTheme() != null ? request.getBackgroundTheme() : "DEFAULT");
        user.setEmailVerified("N");
        user.setLoginFailCount(0);

        // 일반 회원가입인 경우 비밀번호 암호화
        if ("LOCAL".equals(user.getLoginType()) && request.getUserPw() != null) {
            String encodedPassword = passwordEncoder.encode(request.getUserPw());
            user.setUserPw(encodedPassword);
        }

        // DB 저장
        userMapper.insert(user);

        return userId;
    }

    @Override
    @Transactional
    public loginResponse login(loginRequest request) {
        // 1. 사용자 조회
        userVo user = userMapper.findByEmail(request.getLoginId());

        if (user == null) {
            throw new RuntimeException("존재하지 않는 계정입니다.");
        }

        // 2. 계정 상태 확인
        if (!"ACTIVE".equals(user.getUserStatus())) {
            throw new RuntimeException("비활성화된 계정입니다.");
        }

        // 3. 일반 로그인인 경우 비밀번호 확인
        if ("LOCAL".equals(user.getLoginType())) {
            if (user.getUserPw() == null || !passwordEncoder.matches(request.getPassword(), user.getUserPw())) {
                userMapper.increaseLoginFailCount(user.getUserId());
                throw new RuntimeException("비밀번호가 일치하지 않습니다.");
            }
        }

        // 4. 최초 로그인 여부 확인
        boolean firstLogin = (user.getLastLoginDate() == null);

        // 5. 로그인 성공 처리
        userMapper.updateLastLoginDate(user.getUserId());
        userMapper.resetLoginFailCount(user.getUserId());

        // 6. 응답 생성
        loginResponse response = new loginResponse();
        response.setUserId(user.getUserId());
        response.setLoginId(user.getEmail());
        response.setName(user.getUserName());
        response.setEmail(user.getEmail());
        response.setGroupId(user.getGroupId());
        response.setFirstLogin(firstLogin);
        response.setToken("TEMP_TOKEN"); // JWT 토큰 추후 구현
        response.setUserType("USER");
        response.setProfileImage(user.getProfileImage());
        response.setBackgroundTheme(user.getBackgroundTheme());

        return response;
    }

    @Override
    @Transactional
    public void changePassword(passwordChangeRequest request) {
        // 1. 사용자 조회
        userVo user = userMapper.findById(request.getUserId());

        if (user == null) {
            throw new RuntimeException("존재하지 않는 계정입니다.");
        }

        // 2. 소셜 로그인 사용자는 비밀번호 변경 불가
        if (!"LOCAL".equals(user.getLoginType())) {
            throw new RuntimeException("소셜 로그인 사용자는 비밀번호를 변경할 수 없습니다.");
        }

        // 3. 현재 비밀번호 확인
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getUserPw())) {
            throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");
        }

        // 4. 새 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getNewPassword());

        // 5. 비밀번호 업데이트
        userMapper.updatePassword(request.getUserId(), encodedPassword);
    }

    @Override
    @Transactional
    public void updateUserInfo(userRegisterRequest request) {
        // 1. 사용자 조회
        if (request.getUserId() == null || request.getUserId().isEmpty()) {
            throw new RuntimeException("사용자 ID가 필요합니다.");
        }

        userVo user = userMapper.findById(request.getUserId());

        if (user == null) {
            throw new RuntimeException("존재하지 않는 계정입니다.");
        }

        // 2. 사용자 정보 업데이트
        userMapper.updateUserInfo(
                request.getUserId(),
                request.getNickname(),
                request.getGroupId(),
                request.getBirthDate(),
                request.getGender(),
                request.getPhone(),
                request.getProfileImage(),
                request.getBackgroundTheme()
        );
    }

    @Override
    @Transactional
    public void withdraw(userWithdrawRequest request) {
        // 1. 사용자 조회
        userVo user = userMapper.findById(request.getUserId());

        if (user == null) {
            throw new RuntimeException("존재하지 않는 계정입니다.");
        }

        // 2. 일반 로그인 사용자인 경우 비밀번호 확인
        if ("LOCAL".equals(user.getLoginType())) {
            if (request.getPassword() == null ||
                    !passwordEncoder.matches(request.getPassword(), user.getUserPw())) {
                throw new RuntimeException("비밀번호가 일치하지 않습니다.");
            }
        }

        // 3. 탈퇴 처리 (상태 변경)
        userMapper.updateStatus(request.getUserId(), "WITHDRAWN");

        // 4. 탈퇴 사유 로그 저장 (필요시 별도 테이블에 저장 가능)
        // withdrawLogMapper.insert(request.getUserId(), request.getWithdrawReason());
    }
}