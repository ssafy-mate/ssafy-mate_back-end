package com.ssafy.ssafymate.controller;

import com.ssafy.ssafymate.common.ErrorResponseBody;
import com.ssafy.ssafymate.dto.ChatDto.ContentList;
import com.ssafy.ssafymate.dto.ChatDto.RoomList;
import com.ssafy.ssafymate.dto.request.ChatRequestDto;
import com.ssafy.ssafymate.dto.response.ChatHistoryResponseDto;
import com.ssafy.ssafymate.dto.response.ChatHistoryTotalPagesResponseDto;
import com.ssafy.ssafymate.dto.response.ChatRoomResponseDto;
import com.ssafy.ssafymate.service.ChattingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChattingController {

    @Autowired
    private ChattingService chattingService;


    @GetMapping("/room/{userId}")
    @ApiOperation(value = "채팅방 리스트 불러오기", notes = "사용자가 대화한 채팅방 리스트를 불러온다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "인증 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> getRoomList(@PathVariable Long userId) {
        List<RoomList> roomList = chattingService.getRoomList(userId);

        if(roomList == null){
            return ResponseEntity.status(400).body(ErrorResponseBody.of(400, false, "방이 비어있습니다."));
        }
        return ResponseEntity.status(200).body(ChatRoomResponseDto.of(roomList));
    }

    @PostMapping("/log")
    @ApiOperation(value = "대화 내용 불러오기", notes = "대화 내용을 페이징하여 보내준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 403, message = "방 생성 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> getHistoryList(@RequestBody ChatRequestDto chatRequestDto,
                                            @RequestParam(required = false, defaultValue = "1", value = "nowPage") Integer nowPage,
                                            @RequestParam("entryTime") String entryTime) {
        String roomId;
        if (chatRequestDto.getUserId1() > chatRequestDto.getUserId2()) {
            roomId = chatRequestDto.getUserId2() + "-" + chatRequestDto.getUserId1();
        } else {
            roomId = chatRequestDto.getUserId1() + "-" + chatRequestDto.getUserId2();
        }

        if (chattingService.findRoom(roomId) == null) {
            int temp = chattingService.saveRoom(roomId, chatRequestDto.getUserId1(), chatRequestDto.getUserId2());
            if (temp == 0) {
                return ResponseEntity.status(403).body(BaseResponseBody.of(403, false, "방 생성에 실패하였습니다."));
            }
        }
        int size = 5;
        Pageable pageable = PageRequest.of(nowPage-1, 5, Sort.Direction.DESC, "CH.id");

        if (nowPage == 1) {
            int totalLogCount = chattingService.getTotalLogCount(roomId);
            int totalPages = totalLogCount / size;
            if (totalLogCount % size != 0) {
                totalPages += 1;
            }
            List<ContentList> contentList = chattingService.getHistoryList(pageable, roomId, entryTime);
            return ResponseEntity.status(200).body(ChatHistoryTotalPagesResponseDto.of(contentList, totalPages));
        }

        List<ContentList> contentList = chattingService.getHistoryList(pageable, roomId, entryTime);
        return ResponseEntity.status(200).body(ChatHistoryResponseDto.of(contentList));
    }
}
