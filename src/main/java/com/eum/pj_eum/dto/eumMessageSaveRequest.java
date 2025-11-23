package com.eum.pj_eum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class eumMessageSaveRequest {

    private String eumChatId;       // 이음 채팅방 ID
    private String userId;          // 사용자 ID
    private String messageType;     // 메시지 타입 (AI/USER)
    private String messageContent;  // 메시지 내용
    private String questionType;    // 질문 타입 (기분, 수면, 식사 등)

}