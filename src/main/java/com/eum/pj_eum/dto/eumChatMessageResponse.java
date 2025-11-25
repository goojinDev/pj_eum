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
public class eumChatMessageResponse {

    private Long eumMessageId;        // 이음 메시지 ID (AUTO_INCREMENT)
    private Long eumChatId;           // 이음 채팅방 ID
    private Long userId;              // 사용자 ID
    private String messageType;       // 메시지 타입 (AI/USER)
    private String messageContent;    // 메시지 내용
    private LocalDateTime regDate;    // 등록일시
}