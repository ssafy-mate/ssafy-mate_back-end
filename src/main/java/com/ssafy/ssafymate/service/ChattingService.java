package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.dto.ChatDto.ChatMessageDto;
import com.ssafy.ssafymate.dto.ChatDto.ContentList;
import com.ssafy.ssafymate.dto.ChatDto.RoomList;
import com.ssafy.ssafymate.entity.ChattingHistory;
import com.ssafy.ssafymate.entity.ChattingRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChattingService {

    List<RoomList> getRoomList(Long userId);

    List<ContentList> getHistoryList(Pageable pageable, String roomId);

    ChattingRoom findRoom(String roomId);

    int saveRoom(String roomId, Long userId1, Long userId2);

    int saveHistory(ChatMessageDto chatMessageDto);
}
