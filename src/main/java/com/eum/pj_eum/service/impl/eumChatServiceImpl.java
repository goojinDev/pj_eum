package com.eum.pj_eum.service.impl;

import com.eum.pj_eum.dto.emotionScoreSaveRequest;
import com.eum.pj_eum.dto.eumChatListResponse;
import com.eum.pj_eum.dto.eumMessageSaveRequest;
import com.eum.pj_eum.mapper.eumChatMapper;
import com.eum.pj_eum.service.eumChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class eumChatServiceImpl implements eumChatService {

    private final eumChatMapper eumChatMapper;

    /**
     * 이음 채팅 목록 조회
     * 첫 AI 질문 10글자 + ... 형태로 반환
     */
    @Override
    public List<eumChatListResponse> getEumChatList(String userId) {
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
     * 오늘의 이음 채팅방 조회/생성
     * 오늘 날짜의 채팅방이 없으면 새로 생성
     */
    @Override
    @Transactional
    public String getTodayEumChat(String userId) {
        LocalDate today = LocalDate.now();

        // 오늘 날짜의 채팅방 조회
        String eumChatId = eumChatMapper.selectTodayEumChat(userId, today);

        // 없으면 새로 생성
        if (eumChatId == null) {
            eumChatId = UUID.randomUUID().toString();
            eumChatMapper.insertEumChat(eumChatId, userId, today);
        }

        return eumChatId;
    }

    /**
     * 이음 채팅 메시지 저장
     */
    @Override
    @Transactional
    public String saveEumMessage(eumMessageSaveRequest request) {
        String messageId = UUID.randomUUID().toString();

        // 현재 메시지 순서 조회
        Integer currentSeq = eumChatMapper.selectMaxMessageSeq(request.getEumChatId());
        int nextSeq = (currentSeq == null) ? 1 : currentSeq + 1;

        // 메시지 저장
        eumChatMapper.insertEumMessage(
                messageId,
                request.getEumChatId(),
                request.getUserId(),
                request.getMessageType(),
                request.getMessageContent(),
                nextSeq,
                request.getQuestionType()
        );

        // 채팅방 정보 업데이트 (메시지 수, 마지막 메시지 시간)
        eumChatMapper.updateEumChatInfo(request.getEumChatId());

        return messageId;
    }

    /**
     * 감정 점수 저장
     * AI 개발자가 분석한 감정 점수를 DB에 저장
     */
    @Override
    @Transactional
    public void saveEmotionScore(emotionScoreSaveRequest request) {
        String emotionId = UUID.randomUUID().toString();

        eumChatMapper.insertEmotionScore(
                emotionId,
                request.getMessageId(),
                request.getEumMessageId(),
                request.getUserId(),
                request.getEmotionType(),
                request.getEmotionScore(),
                request.getSentiment(),
                request.getSentimentScore(),
                request.getKeywords(),
                request.getModelVersion()
        );
    }

    /**
     * 이음 채팅 삭제 (soft delete)
     */
    @Override
    @Transactional
    public void deleteEumChat(String eumChatId) {
        eumChatMapper.updateEumChatStatus(eumChatId, "DELETED");
    }

}