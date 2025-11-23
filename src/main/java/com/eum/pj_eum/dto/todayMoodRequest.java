package com.eum.pj_eum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class todayMoodRequest {

    private String userId;          // 사용자 ID
    private String moodLevel;       // 기분 레벨 (GOOD, NORMAL, BAD, TIRED)
    private Integer moodScore;      // 기분 점수 (0-100)

}