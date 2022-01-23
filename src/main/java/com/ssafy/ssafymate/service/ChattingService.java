package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.dto.ChatDto.ContentList;
import com.ssafy.ssafymate.dto.ChatDto.RoomList;
import com.ssafy.ssafymate.entity.ChattingHistory;
import com.ssafy.ssafymate.entity.ChattingRoom;

import java.util.List;

public interface ChattingService {

    List<RoomList> getRoomList(Long userId);

    List<ContentList> getHistoryList(String roomId);

    ChattingRoom findRoom(String roomId);

    void saveRoom(String roomId, Long userId1, Long userId2);
}
