package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.dto.ChatDto.ChatMessageDto;
import com.ssafy.ssafymate.dto.ChatDto.ContentList;
import com.ssafy.ssafymate.dto.ChatDto.RoomList;
import com.ssafy.ssafymate.entity.ChattingHistory;
import com.ssafy.ssafymate.entity.ChattingRoom;
import com.ssafy.ssafymate.repository.ChattingHistoryRepository;
import com.ssafy.ssafymate.repository.ChattingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public List<ContentList> getHistoryList(Pageable pageable, String roomId) {

//        List<ContentList> list = chattingHistoryRepository.getHistoryList(roomId).orElse(null);
        List<ContentList> list = chattingHistoryRepository.getHistoryList(pageable, roomId);
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

    @Override
    public int saveHistory(ChatMessageDto chatMessageDto) {

        int temp = chattingHistoryRepository.saveHistory(
                chatMessageDto.getRoomId(),
                chatMessageDto.getSenderId(),
                chatMessageDto.getSentTime(),
                chatMessageDto.getContent()
        );
        if(temp == 1){
            return temp;
        }
        return 0;
    }
}
