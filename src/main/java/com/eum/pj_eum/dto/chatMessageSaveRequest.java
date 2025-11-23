package com.eum.pj_eum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class chatMessageSaveRequest {

    private String chatRoomId;      // 채팅방 ID
    private String userId;          // 사용자 ID
    private String messageType;     // 메시지 타입 (AI/USER)
    private String messageContent;  // 메시지 내용

}