package com.eum.pj_eum.mapper;

import com.eum.pj_eum.dto.chatListResponse;
import com.eum.pj_eum.dto.chatMessageSaveRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface chatMapper {

    /**
     * 일반 채팅 목록 조회
     */
    List<chatListResponse> selectChatList(@Param("userId") Long userId);

    /**
     * 새 채팅방 생성 (AUTO_INCREMENT)
     * Map으로 userId를 전달하고, chatRoomId는 자동 생성 후 Map에 담김
     */
    void insertChatRoom(Map<String, Object> params);

    /**
     * 채팅 메시지 저장 (AUTO_INCREMENT)
     */
    void insertChatMessage(chatMessageSaveRequest request);

    /**
     * 채팅방 제목 수정
     */
    void updateRoomTitle(@Param("chatRoomId") Long chatRoomId, @Param("roomTitle") String roomTitle);

    /**
     * 채팅방 상태 변경
     */
    void updateChatRoomStatus(@Param("chatRoomId") Long chatRoomId, @Param("roomStatus") String roomStatus);
}