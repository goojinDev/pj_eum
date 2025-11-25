package com.eum.pj_eum.service;

import com.eum.pj_eum.dto.chatListResponse;
import com.eum.pj_eum.dto.chatMessageSaveRequest;

import java.util.List;

public interface chatService {

    /**
     * 일반 채팅 목록 조회
     */
    List<chatListResponse> getChatList(Long userId);

    /**
     * 새 채팅방 생성
     */
    Long createChatRoom(Long userId);

    /**
     * 채팅 메시지 저장
     */
    Long saveChatMessage(chatMessageSaveRequest request);

    /**
     * 채팅방 삭제
     */
    void deleteChatRoom(Long chatRoomId);
}