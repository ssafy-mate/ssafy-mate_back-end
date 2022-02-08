package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.dto.ChatDto.ContentList;
import com.ssafy.ssafymate.dto.ChatDto.RoomList;
import com.ssafy.ssafymate.entity.ChattingRoom;
import com.ssafy.ssafymate.repository.ChattingHistoryRepository;
import com.ssafy.ssafymate.repository.ChattingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
    public List<ContentList> getHistoryList(String roomId, Long id, int size) {
        List<ContentList> list;
        if(id == 0){
            list = chattingHistoryRepository.getLatestHistoryList(roomId, size);
        }else{
            list = chattingHistoryRepository.getHistoryList(roomId, id, size);
        }
        return list;
    }

    @Override
    public ChattingRoom findRoom(String roomId) {

        ChattingRoom room = chattingRoomRepository.findByRoomId(roomId).orElse(null);

        return room;
    }

    @Override
    public int saveRoom(String roomId, Long userId1, Long userId2) {

        return chattingRoomRepository.saveRoom(roomId, userId1, userId2);

    }

}
