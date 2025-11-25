package com.eum.pj_eum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class eumChatListResponse {

    private Long eumeChatId;            // 이음 채팅방 ID (AUTO_INCREMENT)
    private Long userId;                // 사용자 ID
    private String chatStatus;          // 채팅 상태 (ACTIVE, DELETED)
    private String firstMessagePreview; // 첫 AI 질문 미리보기 (10글자 + ...)
    private LocalDateTime regDate;      // 등록일시
    private LocalDateTime updDate;      // 수정일시
}