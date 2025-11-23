package com.eum.pj_eum.service;

import com.eum.pj_eum.dto.weatherInfo;

public interface weatherService {

    /**
     * 위도/경도로 날씨 정보 조회
     */
    weatherInfo getWeather(double lat, double lon);

    /**
     * 도시명으로 날씨 정보 조회
     */
    weatherInfo getWeatherByCity(String city);

}