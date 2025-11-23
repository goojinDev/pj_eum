package com.eum.pj_eum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface homeMapper {

    /**
     * 오늘의 기분 저장/수정
     * (이미 있으면 UPDATE, 없으면 INSERT)
     */
    void insertOrUpdateMood(
            @Param("lifeDataId") String lifeDataId,
            @Param("userId") String userId,
            @Param("moodScore") int moodScore,
            @Param("moodLevel") String moodLevel
    );

}