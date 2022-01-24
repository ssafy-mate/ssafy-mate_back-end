package com.ssafy.ssafymate.controller;

import com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer;
import com.ssafy.ssafymate.common.BaseResponseBody;
import com.ssafy.ssafymate.dto.ChatDto.ContentList;
import com.ssafy.ssafymate.dto.ChatDto.RoomList;
import com.ssafy.ssafymate.dto.request.ChatRequestDto;
import com.ssafy.ssafymate.dto.response.ChatHistoryResponseDto;
import com.ssafy.ssafymate.dto.response.ChatRoomResponseDto;
import com.ssafy.ssafymate.entity.ChattingHistory;
import com.ssafy.ssafymate.service.ChattingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChattingController {

    @Autowired
    private ChattingService chattingService;

    @GetMapping("/room/{userId}")
    public ResponseEntity<?> getRoomList(@PathVariable Long userId){
        List<RoomList> roomList = chattingService.getRoomList(userId);
        if(roomList == null){
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, false, "방이 비어있습니다."));
        }
        return ResponseEntity.status(200).body(ChatRoomResponseDto.of(200, true, "", roomList));
    }

    @PostMapping("/log")
    public  ResponseEntity<?> getHistoryList(@RequestBody ChatRequestDto chatRequestDto){
        String roomId;
        if(chatRequestDto.getUserId1() > chatRequestDto.getUserId2()){
            roomId = chatRequestDto.getUserId2() + "-"+ chatRequestDto.getUserId1();
        }else{
            roomId = chatRequestDto.getUserId1() + "-"+ chatRequestDto.getUserId2();
        }

        if(chattingService.findRoom(roomId) == null){
            chattingService.saveRoom(roomId, chatRequestDto.getUserId1(), chatRequestDto.getUserId2());
//                return ResponseEntity.status(403).body(BaseResponseBody.of(403, false, "저장 실패"));
        }

        List<ContentList> contentList = chattingService.getHistoryList(roomId);

        return ResponseEntity.status(200).body(ChatHistoryResponseDto.of(200, true, "", contentList));
    }
}
