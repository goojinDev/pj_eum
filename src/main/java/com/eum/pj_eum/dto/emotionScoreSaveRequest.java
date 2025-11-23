package com.eum.pj_eum.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class emotionScoreSaveRequest {

    private String userId;          // 사용자 ID
    private String messageId;       // 일반 채팅 메시지 ID (null 가능)
    private String eumMessageId;    // 이음 채팅 메시지 ID (null 가능)
    private String emotionType;     // 감정 타입 (HAPPY, SAD, ANGRY 등)
    private Integer emotionScore;   // 감정 점수 (0-100)
    private String sentiment;       // 감정 긍정/부정 (POSITIVE, NEGATIVE, NEUTRAL)
    private Integer sentimentScore; // 감정 점수
    private JsonNode keywords;      // 키워드 (JSON)
    private String modelVersion;    // AI 모델 버전

}