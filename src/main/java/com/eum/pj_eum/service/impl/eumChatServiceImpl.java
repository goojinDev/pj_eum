package com.eum.pj_eum.service.impl;

import com.eum.pj_eum.dto.emotionScoreSaveRequest;
import com.eum.pj_eum.dto.eumChatListResponse;
import com.eum.pj_eum.dto.eumMessageSaveRequest;
import com.eum.pj_eum.mapper.eumChatMapper;
import com.eum.pj_eum.service.eumChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class eumChatServiceImpl implements eumChatService {

    private final eumChatMapper eumChatMapper;

    /**
     * 이음 채팅 목록 조회
     */
    @Override
    public List<eumChatListResponse> getEumChatList(Long userId) {
        List<eumChatListResponse> chatList = eumChatMapper.selectEumChatList(userId);

        // 첫 메시지 미리보기 처리 (10글자 + ...)
        for (eumChatListResponse chat : chatList) {
            String preview = chat.getFirstMessagePreview();
            if (preview != null && preview.length() > 10) {
                chat.setFirstMessagePreview(preview.substring(0, 10) + "...");
            }
        }

        return chatList;
    }

    /**
     * 새 이음 채팅방 생성 (AUTO_INCREMENT)
     */
    @Override
    @Transactional
    public Long createEumChat(Long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);

        eumChatMapper.insertEumChat(params);

        return (Long) params.get("eumeChatId");
    }

    /**
     * 이음 채팅 메시지 저장 (AUTO_INCREMENT)
     */
    @Override
    @Transactional
    public Long saveEumMessage(eumMessageSaveRequest request) {
        eumChatMapper.insertEumMessage(request);
        return request.getEumeChatId(); // messageId는 자동 생성됨
    }

    /**
     * 감정 점수 저장 (AUTO_INCREMENT)
     */
    @Override
    @Transactional
    public void saveEmotionScore(emotionScoreSaveRequest request) {
        eumChatMapper.insertEmotionScore(request);
    }

    /**
     * 이음 채팅 삭제 (soft delete)
     */
    @Override
    @Transactional
    public void deleteEumChat(Long eumeChatId) {
        eumChatMapper.updateEumChatStatus(eumeChatId, "DELETED");
    }
}