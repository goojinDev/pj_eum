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
public class chatListResponse {

    private String chatRoomId;          // 채팅방 ID
    private String userId;              // 사용자 ID
    private String roomTitle;           // 채팅방 제목
    private String roomStatus;          // 채팅방 상태 (ACTIVE, DELETED)
    private String lastMessage;         // 마지막 메시지 (10글자 + ...)
    private LocalDateTime lastMessageDate;  // 마지막 메시지 시간
    private Integer messageCount;       // 메시지 개수

}