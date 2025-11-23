package com.eum.pj_eum.service;

import com.eum.pj_eum.dto.homeGreetingResponse;
import com.eum.pj_eum.dto.todayMoodRequest;

public interface homeService {

    /**
     * 홈 화면 인사말 조회
     */
    homeGreetingResponse getHomeGreeting(String userId);

    /**
     * 오늘의 기분 저장
     */
    void saveTodayMood(todayMoodRequest request);

}