package com.eum.pj_eum.controller;

import com.eum.pj_eum.dto.emotionScoreSaveRequest;
import com.eum.pj_eum.dto.eumChatListResponse;
import com.eum.pj_eum.dto.eumMessageSaveRequest;
import com.eum.pj_eum.service.eumChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "이음 채팅 API", description = "감정 AI 측정 이음 채팅 관련 API")
@RestController
@RequestMapping("/eumChat")
@RequiredArgsConstructor
public class eumChatController {

    private final eumChatService eumChatService;

    @Operation(summary = "이음 채팅 목록 조회",
            description = "하루 단위 이음 채팅 목록을 조회합니다. 첫 AI 질문 10글자 + ... 형태로 표시")
    @GetMapping("/list/{userId}")
    public ResponseEntity<List<eumChatListResponse>> getEumChatList(@PathVariable String userId) {
        List<eumChatListResponse> chatList = eumChatService.getEumChatList(userId);
        return ResponseEntity.ok(chatList);
    }

    @Operation(summary = "오늘의 이음 채팅방 조회/생성",
            description = "오늘 날짜의 이음 채팅방을 조회하고, 없으면 새로 생성합니다.")
    @PostMapping("/today/{userId}")
    public ResponseEntity<String> getTodayEumChat(@PathVariable String userId) {
        String eumChatId = eumChatService.getTodayEumChat(userId);
        return ResponseEntity.ok(eumChatId);
    }

    @Operation(summary = "이음 채팅 메시지 저장",
            description = "AI 질문 또는 사용자 답변을 저장합니다.")
    @PostMapping("/message")
    public ResponseEntity<String> saveEumMessage(@RequestBody eumMessageSaveRequest request) {
        String messageId = eumChatService.saveEumMessage(request);
        return ResponseEntity.ok(messageId);
    }

    @Operation(summary = "감정 점수 저장",
            description = "AI 개발자가 분석한 감정 점수를 저장합니다.")
    @PostMapping("/emotion")
    public ResponseEntity<String> saveEmotionScore(@RequestBody emotionScoreSaveRequest request) {
        eumChatService.saveEmotionScore(request);
        return ResponseEntity.ok("감정 점수가 저장되었습니다.");
    }

    @Operation(summary = "이음 채팅 삭제",
            description = "이음 채팅방을 삭제합니다. (soft delete)")
    @DeleteMapping("/{eumChatId}")
    public ResponseEntity<String> deleteEumChat(@PathVariable String eumChatId) {
        eumChatService.deleteEumChat(eumChatId);
        return ResponseEntity.ok("채팅방이 삭제되었습니다.");
    }

}