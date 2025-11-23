package com.eum.pj_eum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class homeGreetingResponse {

    private String greeting;        // 인사말 (예: "좋은 아침이에요")
    private String userName;        // 사용자 이름
    private weatherInfo weather;    // 날씨 정보

}