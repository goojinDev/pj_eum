package com.eum.pj_eum.mapper;

import com.eum.pj_eum.dto.adminVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface adminMapper {

    /**
     * 관리자 등록 (AUTO_INCREMENT)
     */
    void insert(adminVO admin);

    /**
     * 로그인 ID로 관리자 조회
     */
    adminVO findByLoginId(@Param("adminLoginId") String adminLoginId);

    /**
     * 이메일로 관리자 조회
     */
    adminVO findByEmail(@Param("adminEmail") String adminEmail);

    /**
     * 관리자 ID로 조회
     */
    adminVO findById(@Param("adminId") Long adminId);

    /**
     * 마지막 로그인 시간 업데이트
     */
    void updateLastLoginDate(@Param("adminId") Long adminId);

    /**
     * 로그인 실패 횟수 증가
     */
    void increaseLoginFailCount(@Param("adminId") Long adminId);

    /**
     * 로그인 실패 횟수 초기화
     */
    void resetLoginFailCount(@Param("adminId") Long adminId);

    /**
     * 비밀번호 변경
     */
    void updatePassword(@Param("adminId") Long adminId, @Param("adminPw") String adminPw);
}