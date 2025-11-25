package com.eum.pj_eum.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class emotionScoreSaveRequest {

    private Long userId;            // 사용자 ID
    private Integer depressionScore;// 우울 점수 (0-100)
    private Integer anxietyScore;   // 불안 점수 (0-100)
    private Integer stressScore;    // 스트레스 점수 (0-100)
    private Integer emotionScore;   // 감정 점수 (0-100)
    private String keywords;      // 키워드 (JSON)
    private String modelVersion;    // AI 모델 버전
}