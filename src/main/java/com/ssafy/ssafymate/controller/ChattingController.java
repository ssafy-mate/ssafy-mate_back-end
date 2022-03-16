package com.ssafy.ssafymate.controller;

import com.ssafy.ssafymate.common.ErrorResponseBody;
import com.ssafy.ssafymate.dto.ChatDto.ContentList;
import com.ssafy.ssafymate.dto.ChatDto.RoomList;
import com.ssafy.ssafymate.dto.response.ChatHistoryCursorPagingResponseDto;
import com.ssafy.ssafymate.dto.response.ChatRoomResponseDto;
import com.ssafy.ssafymate.dto.response.ChatUserResponseDto;
import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.service.ChattingService;
import com.ssafy.ssafymate.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChattingController {

    @Autowired
    private ChattingService chattingService;

    @Autowired
    private UserService userService;

    @GetMapping("/infos/{userId}")
    @ApiOperation(value = "상대방 이미지 불러오기", notes = "상대방의 이름과 사진을 불러온다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "요청 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> getUserInfo(@PathVariable("userId") Long userId) {

        try {

            User user = chattingService.getUserInfo(userId);

            if (user == null) {

                return ResponseEntity.status(400).body(ErrorResponseBody.of(400, false, "상대방 정보를 불러올 수 없습니다."));

            }

            return ResponseEntity.status(200).body(ChatUserResponseDto.of(user));

        } catch (Exception e) {

            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server Error, 상대방 정보 불러오기 실패"));

        }

    }

    @GetMapping("/rooms/{userId}")
    @ApiOperation(value = "채팅방 리스트 불러오기", notes = "사용자가 대화한 채팅방 리스트를 불러온다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "조회 실패"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 403, message = "토큰 인증 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> getRoomList(@PathVariable("userId") Long userId, @AuthenticationPrincipal final String token) {

        try {

            if (token == null) {
                return ResponseEntity.status(401).body(ErrorResponseBody.of(401, false, "로그인 후 이용 가능합니다."));
            }

            User sender = userService.getUserByEmail(token);

            List<RoomList> roomList = chattingService.getRoomList(userId);

            if (roomList == null) {
                return ResponseEntity.status(400).body(ErrorResponseBody.of(400, false, "방이 비어있습니다."));
            }

            return ResponseEntity.status(200).body(ChatRoomResponseDto.of(roomList));

        } catch (Exception e) {

            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server Error, 대화 목록 불러오기 실패"));

        }

    }

    @GetMapping("/logs/{roomId}")
    @ApiOperation(value = "대화 내용 불러오기", notes = "대화 내용을 페이징하여 보내준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "방 생성 실패"),
            @ApiResponse(code = 401, message = "선택된 대화 내용 없음"),
            @ApiResponse(code = 403, message = "토큰 인증 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> getHistoryList(
            @PathVariable("roomId") String roomId,
            @RequestParam("nextCursor") Long messageId,
            @AuthenticationPrincipal final String token
    ) {

        if (token == null) {
            return ResponseEntity.status(401).body(ErrorResponseBody.of(401, false, "로그인 후 이용 가능합니다."));
        }

        if(roomId.equals("null")){

            return ResponseEntity.status(401).body(ErrorResponseBody.of(400, false, "선택된 대화 내용이 없습니다."));

        }

        try {


            User sender = userService.getUserByEmail(token);

            String[] ids = roomId.split("-");

            if (chattingService.findRoom(roomId) == null) {

                int saveRoomFlag = chattingService.saveRoom(roomId, Long.parseLong(ids[0]), Long.parseLong(ids[1]));

                if (saveRoomFlag == 0) {

                    return ResponseEntity.status(400).body(ErrorResponseBody.of(400, false, "방 생성에 실패하였습니다."));

                }

            }

            int size = 20;

            List<ContentList> contentList = chattingService.getHistoryList(roomId, messageId, size);

            return ResponseEntity.status(200).body(ChatHistoryCursorPagingResponseDto.of(contentList));

        } catch (Exception e) {

            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server Error, 대화 내용 불러오기 실패"));

        }

    }

}
