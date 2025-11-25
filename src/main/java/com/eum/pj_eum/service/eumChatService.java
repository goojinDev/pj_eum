package com.eum.pj_eum.service;

import com.eum.pj_eum.dto.emotionScoreSaveRequest;
import com.eum.pj_eum.dto.eumChatListResponse;
import com.eum.pj_eum.dto.eumMessageSaveRequest;

import java.util.List;

public interface eumChatService {

    /**
     * 이음 채팅 목록 조회
     */
    List<eumChatListResponse> getEumChatList(Long userId);

    /**
     * 새 이음 채팅방 생성
     */
    Long createEumChat(Long userId);

    /**
     * 이음 채팅 메시지 저장
     */
    Long saveEumMessage(eumMessageSaveRequest request);

    /**
     * 감정 점수 저장
     */
    void saveEmotionScore(emotionScoreSaveRequest request);

    /**
     * 이음 채팅 삭제
     */
    void deleteEumChat(Long eumeChatId);
}