package com.eum.pj_eum.service.impl;

import com.eum.pj_eum.dto.openWeatherResponse;
import com.eum.pj_eum.dto.weatherInfo;
import com.eum.pj_eum.service.weatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class weatherServiceImpl implements weatherService {

    private final RestTemplate restTemplate;

    // application.properties에서 설정
    @Value("${openweather.api.key:}")
    private String apiKey;

    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";

    /**
     * 위도/경도로 날씨 정보 조회
     */
    @Override
    public weatherInfo getWeather(double lat, double lon) {
        try {
            // API 키가 없으면 기본값 반환
            if (apiKey == null || apiKey.isEmpty()) {
                log.warn("OpenWeather API 키가 설정되지 않았습니다. 기본값을 반환합니다.");
                return getDefaultWeather();
            }

            // API 호출
            String url = String.format(
                    "%s?lat=%f&lon=%f&appid=%s&units=metric&lang=kr",
                    API_URL, lat, lon, apiKey
            );

            openWeatherResponse response = restTemplate.getForObject(url, openWeatherResponse.class);

            if (response == null) {
                return getDefaultWeather();
            }

            // 응답을 weatherInfo로 변환
            return weatherInfo.builder()
                    .temperature(String.format("%.0f°C", response.getMain().getTemp()))
                    .weatherStatus(translateWeatherStatus(response.getWeather().get(0).getMain()))
                    .description(response.getWeather().get(0).getDescription())
                    .humidity(response.getMain().getHumidity())
                    .windSpeed(response.getWind().getSpeed())
                    .build();

        } catch (Exception e) {
            log.error("날씨 정보 조회 실패: {}", e.getMessage());
            return getDefaultWeather();
        }
    }

    /**
     * 도시명으로 날씨 정보 조회
     */
    @Override
    public weatherInfo getWeatherByCity(String city) {
        try {
            if (apiKey == null || apiKey.isEmpty()) {
                return getDefaultWeather();
            }

            String url = String.format(
                    "%s?q=%s&appid=%s&units=metric&lang=kr",
                    API_URL, city, apiKey
            );

            openWeatherResponse response = restTemplate.getForObject(url, openWeatherResponse.class);

            if (response == null) {
                return getDefaultWeather();
            }

            return weatherInfo.builder()
                    .temperature(String.format("%.0f°C", response.getMain().getTemp()))
                    .weatherStatus(translateWeatherStatus(response.getWeather().get(0).getMain()))
                    .description(response.getWeather().get(0).getDescription())
                    .humidity(response.getMain().getHumidity())
                    .windSpeed(response.getWind().getSpeed())
                    .build();

        } catch (Exception e) {
            log.error("날씨 정보 조회 실패: {}", e.getMessage());
            return getDefaultWeather();
        }
    }

    /**
     * 영문 날씨 상태를 한글로 변환
     */
    private String translateWeatherStatus(String weatherMain) {
        switch (weatherMain.toLowerCase()) {
            case "clear":
                return "맑음";
            case "clouds":
                return "흐림";
            case "rain":
                return "비";
            case "drizzle":
                return "이슬비";
            case "thunderstorm":
                return "천둥번개";
            case "snow":
                return "눈";
            case "mist":
            case "fog":
                return "안개";
            default:
                return "보통";
        }
    }

    /**
     * 기본 날씨 정보 반환 (API 실패 시)
     */
    private weatherInfo getDefaultWeather() {
        return weatherInfo.builder()
                .temperature("18°C")
                .weatherStatus("맑음")
                .description("쾌적한 날씨입니다")
                .humidity(60)
                .windSpeed(2.5)
                .build();
    }

}