package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.dto.ChatDto.ContentList;
import com.ssafy.ssafymate.dto.ChatDto.RoomList;
import com.ssafy.ssafymate.entity.ChattingHistory;
import com.ssafy.ssafymate.entity.ChattingRoom;
import com.ssafy.ssafymate.repository.ChattingHistoryRepository;
import com.ssafy.ssafymate.repository.ChattingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChattingServiceImpl implements ChattingService{

    @Autowired
    private ChattingRoomRepository chattingRoomRepository;

    @Autowired
    private ChattingHistoryRepository chattingHistoryRepository;

    @Override
    public List<RoomList> getRoomList(Long userId) {

        List<RoomList> list = chattingRoomRepository.getChattingRoom(userId).orElse(null);
        return list;
    }

    @Override
    public List<ContentList> getHistoryList(String roomId) {

        List<ContentList> list = chattingHistoryRepository.getHistoryList(roomId).orElse(null);
        return list;
    }

    @Override
    public ChattingRoom findRoom(String roomId) {

        ChattingRoom room = chattingRoomRepository.findByRoomId(roomId).orElse(null);

        return room;
    }

    @Override
    public void saveRoom(String roomId, Long userId1, Long userId2) {

        chattingRoomRepository.saveRoom(roomId, userId1, userId2);
    }
}
