package com.eum.pj_eum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class weatherInfo {

    private String temperature;     // 온도 (예: "18°C")
    private String weatherStatus;   // 날씨 상태 (맑음, 흐림 등)
    private String description;     // 상세 설명
    private Integer humidity;       // 습도
    private Double windSpeed;       // 풍속

}