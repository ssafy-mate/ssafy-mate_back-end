package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.dto.ChatDto.ContentList;
import com.ssafy.ssafymate.dto.ChatDto.RoomList;
import com.ssafy.ssafymate.entity.ChattingRoom;

import java.util.List;

public interface ChattingService {

    List<RoomList> getRoomList(Long userId);

    List<ContentList> getHistoryList(String roomId, Long id, int size);

    ChattingRoom findRoom(String roomId);

    int saveRoom(String roomId, Long userId1, Long userId2);

}
