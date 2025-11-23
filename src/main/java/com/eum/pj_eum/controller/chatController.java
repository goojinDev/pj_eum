package com.eum.pj_eum.controller;

import com.eum.pj_eum.dto.chatListResponse;
import com.eum.pj_eum.dto.chatMessageSaveRequest;
import com.eum.pj_eum.service.chatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "일반 채팅 API", description = "ChatGPT 같은 일반 채팅 기능 API")
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class chatController {

    private final chatService chatService;

    @Operation(summary = "일반 채팅 목록 조회",
            description = "사용자의 일반 채팅 목록을 조회합니다. 마지막 메시지 10글자 + ... 형태로 표시")
    @GetMapping("/list/{userId}")
    public ResponseEntity<List<chatListResponse>> getChatList(@PathVariable String userId) {
        List<chatListResponse> chatList = chatService.getChatList(userId);
        return ResponseEntity.ok(chatList);
    }

    @Operation(summary = "새 채팅방 생성",
            description = "새로운 일반 채팅방을 생성합니다.")
    @PostMapping("/create/{userId}")
    public ResponseEntity<String> createChatRoom(@PathVariable String userId) {
        String chatRoomId = chatService.createChatRoom(userId);
        return ResponseEntity.ok(chatRoomId);
    }

    @Operation(summary = "채팅 메시지 저장",
            description = "사용자 질문 또는 AI 답변을 저장합니다.")
    @PostMapping("/message")
    public ResponseEntity<String> saveChatMessage(@RequestBody chatMessageSaveRequest request) {
        String messageId = chatService.saveChatMessage(request);
        return ResponseEntity.ok(messageId);
    }


    @Operation(summary = "채팅방 삭제",
            description = "채팅방을 삭제합니다. (soft delete)")
    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<String> deleteChatRoom(@PathVariable String chatRoomId) {
        chatService.deleteChatRoom(chatRoomId);
        return ResponseEntity.ok("채팅방이 삭제되었습니다.");
    }

}