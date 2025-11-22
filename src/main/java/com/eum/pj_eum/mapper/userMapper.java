package com.eum.pj_eum.mapper;

import com.eum.pj_eum.dto.userVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

@Mapper
public interface userMapper {

    /**
     * 사용자 등록
     */
    void insert(userVo user);

    /**
     * 이메일로 사용자 조회
     */
    userVo findByEmail(@Param("email") String email);

    /**
     * 사용자 ID로 조회
     */
    userVo findById(@Param("userId") String userId);

    /**
     * 마지막 로그인 시간 업데이트
     */
    void updateLastLoginDate(@Param("userId") String userId);

    /**
     * 로그인 실패 횟수 증가
     */
    void increaseLoginFailCount(@Param("userId") String userId);

    /**
     * 로그인 실패 횟수 초기화
     */
    void resetLoginFailCount(@Param("userId") String userId);

    /**
     * 비밀번호 변경
     */
    void updatePassword(@Param("userId") String userId, @Param("userPw") String userPw);

    /**
     * 사용자 상태 변경
     */
    void updateStatus(@Param("userId") String userId, @Param("userStatus") String userStatus);


    /**
     * 프로필 이미지 업데이트
     */
    void updateProfileImage(@Param("userId") String userId, @Param("profileImage") String profileImage);

    /**
     * 추가 정보 업데이트
     */
    void updateAdditionalInfo(
            @Param("userId") String userId,
            @Param("nickname") String nickname,
            @Param("groupId") String groupId,
            @Param("birthDate") LocalDate birthDate,
            @Param("gender") String gender,
            @Param("phone") String phone,
            @Param("backgroundTheme") String backgroundTheme
    );

    /**
     * 사용자 정보 업데이트 (닉네임, 전화번호, 주소, 테마 등)
     */
    void updateUserInfo(
            @Param("userId") String userId,
            @Param("nickname") String nickname,
            @Param("groupId") String groupId,
            @Param("birthDate") LocalDate birthDate,
            @Param("gender") String gender,
            @Param("phone") String phone,
            @Param("profileImage") String profileImage,
            @Param("backgroundTheme") String backgroundTheme
    );
}