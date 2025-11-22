package com.eum.pj_eum.controller;

import com.eum.pj_eum.dto.adminRegisterRequest;
import com.eum.pj_eum.dto.loginRequest;
import com.eum.pj_eum.dto.loginResponse;
import com.eum.pj_eum.dto.passwordChangeRequest;
import com.eum.pj_eum.service.adminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "관리자 API", description = "관리자 회원가입, 로그인, 비밀번호 변경 API")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class adminController {

    private final adminService adminService;

    @Operation(summary = "관리자 회원가입", description = "새로운 관리자 계정을 생성합니다.")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody adminRegisterRequest request) {
        String adminId = adminService.register(request);
        return ResponseEntity.ok(adminId);
    }

    @Operation(summary = "관리자 로그인", description = "관리자 계정으로 로그인합니다.")
    @PostMapping("/login")
    public ResponseEntity<loginResponse> login(@RequestBody loginRequest request) {
        loginResponse response = adminService.login(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "비밀번호 변경", description = "관리자 비밀번호를 변경합니다.")
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody passwordChangeRequest request) {
        adminService.changePassword(request);
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }
}