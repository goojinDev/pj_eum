package com.eum.pj_eum.controller;

import com.eum.pj_eum.dto.homeGreetingResponse;
import com.eum.pj_eum.dto.todayMoodRequest;
import com.eum.pj_eum.service.homeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "홈 화면 API", description = "홈 화면 관련 API (인사말, 날씨, 기분 등)")
@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class homeController {

    private final homeService homeService;

    @Operation(summary = "홈 화면 인사말 조회",
            description = "시간대별 인사말과 실시간 날씨 정보를 조회합니다.")
    @GetMapping("/greeting/{userId}")
    public ResponseEntity<homeGreetingResponse> getHomeGreeting(@PathVariable String userId) {
        homeGreetingResponse response = homeService.getHomeGreeting(userId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "오늘의 기분 저장",
            description = "사용자의 오늘 기분을 저장합니다. (좋아요, 보통, 안좋아요, 피곤해요)")
    @PostMapping("/mood")
    public ResponseEntity<String> saveTodayMood(@RequestBody todayMoodRequest request) {
        homeService.saveTodayMood(request);
        return ResponseEntity.ok("오늘의 기분이 저장되었습니다.");
    }

}