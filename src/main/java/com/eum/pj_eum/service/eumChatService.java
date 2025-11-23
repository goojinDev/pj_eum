package com.eum.pj_eum.service;

import com.eum.pj_eum.dto.emotionScoreSaveRequest;
import com.eum.pj_eum.dto.eumChatListResponse;
import com.eum.pj_eum.dto.eumMessageSaveRequest;

import java.util.List;

public interface eumChatService {

    /**
     * 이음 채팅 목록 조회
     */
    List<eumChatListResponse> getEumChatList(String userId);

    /**
     * 오늘의 이음 채팅방 조회/생성
     */
    String getTodayEumChat(String userId);

    /**
     * 이음 채팅 메시지 저장
     */
    String saveEumMessage(eumMessageSaveRequest request);

    /**
     * 감정 점수 저장
     */
    void saveEmotionScore(emotionScoreSaveRequest request);

    /**
     * 이음 채팅 삭제
     */
    void deleteEumChat(String eumChatId);

}