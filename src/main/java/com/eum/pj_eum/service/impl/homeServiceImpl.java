package com.eum.pj_eum.service.impl;

import com.eum.pj_eum.dto.homeGreetingResponse;
import com.eum.pj_eum.dto.todayMoodRequest;
import com.eum.pj_eum.dto.weatherInfo;
import com.eum.pj_eum.mapper.homeMapper;
import com.eum.pj_eum.mapper.userMapper;
import com.eum.pj_eum.dto.userVo;
import com.eum.pj_eum.service.homeService;
import com.eum.pj_eum.service.weatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class homeServiceImpl implements homeService {

    private final homeMapper homeMapper;
    private final userMapper userMapper;
    private final weatherService weatherService;

    /**
     * 홈 화면 인사말 조회
     * 시간대별 인사말 + 실시간 날씨 정보
     */
    @Override
    public homeGreetingResponse getHomeGreeting(String userId) {
        // 사용자 정보 조회
        userVo user = userMapper.findById(userId);

        // 시간대별 인사말 생성
        String greeting = getTimeBasedGreeting();

        // 실시간 날씨 정보 조회
        // TODO: 사용자 위치 정보가 있다면 해당 위치의 날씨 조회
        // 현재는 서울 기준 (위도: 37.5665, 경도: 126.9780)
        weatherInfo weather = weatherService.getWeather(37.5665, 126.9780);

        // 또는 도시명으로 조회 (예: 판교 = Pangyo)
        // weatherInfo weather = weatherService.getWeatherByCity("Seoul");

        return homeGreetingResponse.builder()
                .greeting(greeting)
                .userName(user.getUserName())
                .weather(weather)
                .build();
    }

    /**
     * 시간대별 인사말 생성
     */
    private String getTimeBasedGreeting() {
        LocalTime now = LocalTime.now();

        if (now.isBefore(LocalTime.of(12, 0))) {
            return "좋은 아침이에요";
        } else if (now.isBefore(LocalTime.of(18, 0))) {
            return "좋은 오후에요";
        } else {
            return "좋은 저녁이에요";
        }
    }

    /**
     * 오늘의 기분 저장
     */
    @Override
    @Transactional
    public void saveTodayMood(todayMoodRequest request) {
        String lifeDataId = UUID.randomUUID().toString();

        // 기분 레벨에 따른 점수 설정
        int moodScore = getMoodScore(request.getMoodLevel());

        homeMapper.insertOrUpdateMood(
                lifeDataId,
                request.getUserId(),
                moodScore,
                request.getMoodLevel()
        );
    }

    /**
     * 기분 레벨에 따른 점수 반환
     */
    private int getMoodScore(String moodLevel) {
        switch (moodLevel) {
            case "GOOD":      // 좋아요
                return 100;
            case "NORMAL":    // 보통
                return 70;
            case "BAD":       // 안좋아요
                return 40;
            case "TIRED":     // 피곤해요
                return 30;
            default:
                return 50;
        }
    }

}