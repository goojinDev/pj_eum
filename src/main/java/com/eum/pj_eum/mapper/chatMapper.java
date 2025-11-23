package com.eum.pj_eum.mapper;

import com.eum.pj_eum.dto.chatListResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface chatMapper {

    /**
     * 일반 채팅 목록 조회
     */
    List<chatListResponse> selectChatList(@Param("userId") String userId);

    /**
     * 새 채팅방 생성
     */
    void insertChatRoom(
            @Param("chatRoomId") String chatRoomId,
            @Param("userId") String userId
    );

    /**
     * 현재 채팅방의 최대 메시지 순서 조회
     */
    Integer selectMaxMessageSeq(@Param("chatRoomId") String chatRoomId);

    /**
     * 채팅 메시지 저장
     */
    void insertChatMessage(
            @Param("messageId") String messageId,
            @Param("chatRoomId") String chatRoomId,
            @Param("userId") String userId,
            @Param("messageType") String messageType,
            @Param("messageContent") String messageContent,
            @Param("messageSeq") int messageSeq
    );

    /**
     * 채팅방 정보 업데이트 (마지막 메시지, 메시지 수)
     */
    void updateChatRoomInfo(
            @Param("chatRoomId") String chatRoomId,
            @Param("lastMessage") String lastMessage
    );

    /**
     * 채팅방 제목 수정
     */
    void updateRoomTitle(
            @Param("chatRoomId") String chatRoomId,
            @Param("roomTitle") String roomTitle
    );

    /**
     * 채팅방 상태 변경
     */
    void updateChatRoomStatus(
            @Param("chatRoomId") String chatRoomId,
            @Param("roomStatus") String roomStatus
    );

}