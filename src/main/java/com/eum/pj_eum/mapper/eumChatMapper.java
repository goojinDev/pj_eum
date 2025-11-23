package com.eum.pj_eum.mapper;

import com.eum.pj_eum.dto.eumChatListResponse;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface eumChatMapper {

    /**
     * 이음 채팅 목록 조회
     */
    List<eumChatListResponse> selectEumChatList(@Param("userId") String userId);

    /**
     * 오늘 날짜의 이음 채팅방 조회
     */
    String selectTodayEumChat(
            @Param("userId") String userId,
            @Param("chatDate") LocalDate chatDate
    );

    /**
     * 새 이음 채팅방 생성
     */
    void insertEumChat(
            @Param("eumChatId") String eumChatId,
            @Param("userId") String userId,
            @Param("chatDate") LocalDate chatDate
    );

    /**
     * 현재 채팅방의 최대 메시지 순서 조회
     */
    Integer selectMaxMessageSeq(@Param("eumChatId") String eumChatId);

    /**
     * 이음 채팅 메시지 저장
     */
    void insertEumMessage(
            @Param("messageId") String messageId,
            @Param("eumChatId") String eumChatId,
            @Param("userId") String userId,
            @Param("messageType") String messageType,
            @Param("messageContent") String messageContent,
            @Param("messageSeq") int messageSeq,
            @Param("questionType") String questionType
    );

    /**
     * 채팅방 정보 업데이트 (메시지 수, 마지막 메시지 시간)
     */
    void updateEumChatInfo(@Param("eumChatId") String eumChatId);

    /**
     * 감정 점수 저장
     */
    void insertEmotionScore(
            @Param("emotionId") String emotionId,
            @Param("messageId") String messageId,
            @Param("eumMessageId") String eumMessageId,
            @Param("userId") String userId,
            @Param("emotionType") String emotionType,
            @Param("emotionScore") Integer emotionScore,
            @Param("sentiment") String sentiment,
            @Param("sentimentScore") Integer sentimentScore,
            @Param("keywords") JsonNode keywords,
            @Param("modelVersion") String modelVersion
    );

    /**
     * 이음 채팅 상태 변경
     */
    void updateEumChatStatus(
            @Param("eumChatId") String eumChatId,
            @Param("chatStatus") String chatStatus
    );

}