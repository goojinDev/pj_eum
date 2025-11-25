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

    private Long chatRoomId;            // 채팅방 ID (AUTO_INCREMENT)
    private Long userId;                // 사용자 ID
    private String roomTitle;           // 채팅방 제목
    private String roomStatus;          // 채팅방 상태 (ACTIVE, DELETED)
    private LocalDateTime regDate;      // 등록일시
    private LocalDateTime updDate;      // 수정일시
}