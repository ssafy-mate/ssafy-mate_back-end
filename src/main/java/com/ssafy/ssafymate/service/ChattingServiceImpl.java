package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.dto.ChatDto.ContentList;
import com.ssafy.ssafymate.dto.ChatDto.RoomList;
import com.ssafy.ssafymate.entity.ChattingRoom;
import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.repository.ChattingHistoryRepository;
import com.ssafy.ssafymate.repository.ChattingRoomRepository;
import com.ssafy.ssafymate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChattingServiceImpl implements ChattingService{

    @Autowired
    private ChattingRoomRepository chattingRoomRepository;

    @Autowired
    private ChattingHistoryRepository chattingHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserInfo(Long userId) {

        return userRepository.findById(userId).orElse(null);

    }

    @Override
    public List<RoomList> getRoomList(Long userId) {

        return chattingRoomRepository.getChattingRoom(userId).orElse(null);

    }

    @Override
    public List<ContentList> getHistoryList(String roomId, Long id, int size) {

        List<ContentList> list;
        if(id == -1){
            list = chattingHistoryRepository.getLatestHistoryList(roomId, size);
        }else{
            list = chattingHistoryRepository.getHistoryList(roomId, id, size);
        }
        return list;

    }

    @Override
    public ChattingRoom findRoom(String roomId) {

        return chattingRoomRepository.findByRoomId(roomId).orElse(null);

    }

    @Override
    public int saveRoom(String roomId, Long userId1, Long userId2) {

        return chattingRoomRepository.saveRoom(roomId, userId1, userId2);

    }

}
