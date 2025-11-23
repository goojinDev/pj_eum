package com.eum.pj_eum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class eumChatListResponse {

    private String eumChatId;           // 채팅방 ID
    private String userId;              // 사용자 ID
    private LocalDate chatDate;         // 채팅 날짜
    private String chatStatus;          // 채팅 상태 (ACTIVE, DELETED)
    private String isCompleted;         // 완료 여부 (Y/N)
    private Integer messageCount;       // 메시지 개수
    private String firstMessagePreview; // 첫 AI 질문 미리보기 (10글자 + ...)
    private LocalDateTime firstMessageDate;  // 첫 메시지 시간
    private LocalDateTime lastMessageDate;   // 마지막 메시지 시간

}