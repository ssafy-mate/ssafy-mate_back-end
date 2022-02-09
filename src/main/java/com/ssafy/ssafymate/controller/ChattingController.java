package com.ssafy.ssafymate.controller;

import com.ssafy.ssafymate.common.ErrorResponseBody;
import com.ssafy.ssafymate.dto.ChatDto.ContentList;
import com.ssafy.ssafymate.dto.ChatDto.RoomList;
import com.ssafy.ssafymate.dto.response.ChatHistoryCursorPagingResponseDto;
import com.ssafy.ssafymate.dto.response.ChatHistoryResponseDto;
import com.ssafy.ssafymate.dto.response.ChatRoomResponseDto;
import com.ssafy.ssafymate.service.ChattingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChattingController {

    @Autowired
    private ChattingService chattingService;


    @GetMapping("/rooms/{userId}")
    @ApiOperation(value = "채팅방 리스트 불러오기", notes = "사용자가 대화한 채팅방 리스트를 불러온다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "인증 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> getRoomList(@PathVariable("userId") Long userId) {
        List<RoomList> roomList = chattingService.getRoomList(userId);

        if (roomList == null) {
            return ResponseEntity.status(400).body(ErrorResponseBody.of(400, false, "방이 비어있습니다."));
        }
        return ResponseEntity.status(200).body(ChatRoomResponseDto.of(roomList));
    }

    @GetMapping("/logs/{roomId}")
    @ApiOperation(value = "대화 내용 불러오기", notes = "대화 내용을 페이징하여 보내준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 403, message = "방 생성 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> getHistoryList(
            @PathVariable("roomId") String roomId,
            @RequestParam("nextCursor") Long messageId
    ) {
        // 스트링 파싱하기
        String[] ids = roomId.split("-");

        // 채팅방의 존재 여부 확인
        if (chattingService.findRoom(roomId) == null) {
            // 채팅방이 없으면 채팅방 개설
            int temp = chattingService.saveRoom(roomId, Long.parseLong(ids[0]), Long.parseLong(ids[1]));
            if (temp == 0) {
                return ResponseEntity.status(403).body(ErrorResponseBody.of(403, false, "방 생성에 실패하였습니다."));
            }
        }
        // 20개씩 전송
        int size = 20;

        List<ContentList> contentList = chattingService.getHistoryList(roomId, messageId, size);

        return ResponseEntity.status(200).body(ChatHistoryCursorPagingResponseDto.of(contentList));
    }
}
