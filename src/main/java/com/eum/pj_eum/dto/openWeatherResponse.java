package com.eum.pj_eum.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class openWeatherResponse {

    private Main main;
    private List<Weather> weather;
    private Wind wind;
    private String name;  // 지역명

    @Data
    public static class Main {
        private Double temp;        // 온도
        private Integer humidity;   // 습도

        @JsonProperty("feels_like")
        private Double feelsLike;   // 체감온도
    }

    @Data
    public static class Weather {
        private String main;        // 날씨 메인 (Clear, Clouds 등)
        private String description; // 상세 설명
        private String icon;        // 아이콘 코드
    }

    @Data
    public static class Wind {
        private Double speed;       // 풍속
    }

}