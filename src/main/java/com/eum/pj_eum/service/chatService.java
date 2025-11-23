package com.eum.pj_eum.service;

import com.eum.pj_eum.dto.chatListResponse;
import com.eum.pj_eum.dto.chatMessageSaveRequest;

import java.util.List;

public interface chatService {

    /**
     * 일반 채팅 목록 조회
     */
    List<chatListResponse> getChatList(String userId);

    /**
     * 새 채팅방 생성
     */
    String createChatRoom(String userId);

    /**
     * 채팅 메시지 저장
     */
    String saveChatMessage(chatMessageSaveRequest request);


    /**
     * 채팅방 삭제
     */
    void deleteChatRoom(String chatRoomId);

}