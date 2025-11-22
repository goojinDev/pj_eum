package com.eum.pj_eum.controller;

import com.eum.pj_eum.dto.loginRequest;
import com.eum.pj_eum.dto.loginResponse;
import com.eum.pj_eum.dto.passwordChangeRequest;
import com.eum.pj_eum.dto.userRegisterRequest;
import com.eum.pj_eum.dto.userWithdrawRequest;
import com.eum.pj_eum.service.userService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자 API", description = "사용자 회원가입, 로그인, 비밀번호 변경, 탈퇴 API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class userController {

    private final userService userService;

    @Operation(summary = "사용자 회원가입/추가정보입력",
            description = "신규 회원가입 또는 소셜 로그인 후 추가 정보 입력 (userId 유무로 구분)")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody userRegisterRequest request) {
        String userId = userService.register(request);
        return ResponseEntity.ok(userId);
    }

    @Operation(summary = "사용자 로그인", description = "사용자 계정으로 로그인합니다.")
    @PostMapping("/login")
    public ResponseEntity<loginResponse> login(@RequestBody loginRequest request) {
        loginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "비밀번호 변경", description = "사용자 비밀번호를 변경합니다. (일반 로그인만)")
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody passwordChangeRequest request) {
        userService.changePassword(request);
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }

    @Operation(summary = "사용자 정보 수정", description = "닉네임, 전화번호, 프로필이미지, 배경테마 등을 수정합니다.")
    @PatchMapping("/update")  // PUT → PATCH로 변경
    public ResponseEntity<String> updateUserInfo(@RequestBody userRegisterRequest request) {
        userService.updateUserInfo(request);
        return ResponseEntity.ok("사용자 정보가 수정되었습니다.");
    }

    @Operation(summary = "회원 탈퇴", description = "사용자 계정을 탈퇴합니다.")
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody userWithdrawRequest request) {
        userService.withdraw(request);
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }
}