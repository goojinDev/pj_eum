package com.eum.pj_eum.mapper;

import com.eum.pj_eum.dto.emotionScoreSaveRequest;
import com.eum.pj_eum.dto.eumChatListResponse;
import com.eum.pj_eum.dto.eumMessageSaveRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface eumChatMapper {

    /**
     * 이음 채팅 목록 조회
     */
    List<eumChatListResponse> selectEumChatList(@Param("userId") Long userId);

    /**
     * 새 이음 채팅방 생성 (AUTO_INCREMENT)
     * Map으로 userId를 전달하고, eumeChatId는 자동 생성 후 Map에 담김
     */
    void insertEumChat(Map<String, Object> params);

    /**
     * 이음 채팅 메시지 저장 (AUTO_INCREMENT)
     */
    void insertEumMessage(eumMessageSaveRequest request);

    /**
     * 감정 점수 저장 (AUTO_INCREMENT)
     */
    void insertEmotionScore(emotionScoreSaveRequest request);

    /**
     * 이음 채팅 상태 변경
     */
    void updateEumChatStatus(@Param("eumeChatId") Long eumeChatId, @Param("chatStatus") String chatStatus);
}