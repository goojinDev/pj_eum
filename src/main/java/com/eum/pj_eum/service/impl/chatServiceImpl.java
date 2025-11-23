package com.eum.pj_eum.service.impl;

import com.eum.pj_eum.dto.chatListResponse;
import com.eum.pj_eum.dto.chatMessageSaveRequest;
import com.eum.pj_eum.mapper.chatMapper;
import com.eum.pj_eum.service.chatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class chatServiceImpl implements chatService {

    private final chatMapper chatMapper;

    /**
     * 일반 채팅 목록 조회
     * 마지막 메시지 10글자 + ... 형태로 반환
     */
    @Override
    public List<chatListResponse> getChatList(String userId) {
        List<chatListResponse> chatList = chatMapper.selectChatList(userId);

        // 마지막 메시지 미리보기 처리 (10글자 + ...)
        for (chatListResponse chat : chatList) {
            String lastMessage = chat.getLastMessage();
            if (lastMessage != null && lastMessage.length() > 10) {
                chat.setLastMessage(lastMessage.substring(0, 10) + "...");
            }
        }

        return chatList;
    }

    /**
     * 새 채팅방 생성
     */
    @Override
    @Transactional
    public String createChatRoom(String userId) {
        String chatRoomId = UUID.randomUUID().toString();

        chatMapper.insertChatRoom(chatRoomId, userId);

        return chatRoomId;
    }

    /**
     * 채팅 메시지 저장
     */
    @Override
    @Transactional
    public String saveChatMessage(chatMessageSaveRequest request) {
        String messageId = UUID.randomUUID().toString();

        // 현재 메시지 순서 조회
        Integer currentSeq = chatMapper.selectMaxMessageSeq(request.getChatRoomId());
        int nextSeq = (currentSeq == null) ? 1 : currentSeq + 1;

        // 메시지 저장
        chatMapper.insertChatMessage(
                messageId,
                request.getChatRoomId(),
                request.getUserId(),
                request.getMessageType(),
                request.getMessageContent(),
                nextSeq
        );

        // 채팅방 정보 업데이트 (마지막 메시지, 메시지 수)
        chatMapper.updateChatRoomInfo(
                request.getChatRoomId(),
                request.getMessageContent()
        );

        return messageId;
    }


    /**
     * 채팅방 삭제 (soft delete)
     */
    @Override
    @Transactional
    public void deleteChatRoom(String chatRoomId) {
        chatMapper.updateChatRoomStatus(chatRoomId, "DELETED");
    }

}