package com.eum.pj_eum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class emotionResponse {

    private Long emotionId;            // 감정 분석 ID (AUTO_INCREMENT)
    private Long userId;               // 사용자 ID
    private LocalDate analysisDate;    // 분석 날짜
    private Integer depressionScore;    // 우울 점수
    private Integer anxietyScore;       // 불안 점수
    private Integer stressScore;        // 스트레스 점수
    private Integer emotionScore;       // 종합 감정 점수
    private String keywords;           // 키워드 (JSON 배열 문자열)
    private String modelVersion;       // AI 모델 버전
    private LocalDateTime regDate;     // 등록일시
}