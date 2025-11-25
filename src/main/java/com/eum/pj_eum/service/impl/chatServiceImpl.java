package com.eum.pj_eum.service.impl;

import com.eum.pj_eum.dto.chatListResponse;
import com.eum.pj_eum.dto.chatMessageSaveRequest;
import com.eum.pj_eum.mapper.chatMapper;
import com.eum.pj_eum.service.chatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class chatServiceImpl implements chatService {

    private final chatMapper chatMapper;

    /**
     * 일반 채팅 목록 조회
     */
    @Override
    public List<chatListResponse> getChatList(Long userId) {
        return chatMapper.selectChatList(userId);
    }

    /**
     * 새 채팅방 생성 (AUTO_INCREMENT)
     */
    @Override
    @Transactional
    public Long createChatRoom(Long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);

        chatMapper.insertChatRoom(params);

        return (Long) params.get("chatRoomId");
    }

    /**
     * 채팅 메시지 저장 (AUTO_INCREMENT)
     */
    @Override
    @Transactional
    public Long saveChatMessage(chatMessageSaveRequest request) {
        chatMapper.insertChatMessage(request);
        return request.getChatRoomId(); // messageId는 자동 생성됨
    }

    /**
     * 채팅방 삭제 (soft delete)
     */
    @Override
    @Transactional
    public void deleteChatRoom(Long chatRoomId) {
        chatMapper.updateChatRoomStatus(chatRoomId, "DELETED");
    }
}