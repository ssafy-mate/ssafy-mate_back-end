package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.dto.ChatDto.ContentList;
import com.ssafy.ssafymate.dto.ChatDto.RoomList;
import com.ssafy.ssafymate.entity.ChattingRoom;
import com.ssafy.ssafymate.entity.User;

import java.util.List;

public interface ChattingService {

    // 채팅 상대의 사진과 이메일 데이터 조회
    User getUserInfo(Long userId);

    // 사용자의 채팅방 리스트를 조회
    List<RoomList> getRoomList(Long userId);

    // 상대방과의 대화 내용 리스트 페이징 조회
    List<ContentList> getHistoryList(String roomId, Long id, int size);

    // 상대방과의 채팅방이 있는지 조회
    ChattingRoom findRoom(String roomId);

    // 채팅방 생성
    int saveRoom(String roomId, Long userId1, Long userId2);

}
