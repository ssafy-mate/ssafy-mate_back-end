package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.common.ErrorResponseBody;
import com.ssafy.ssafymate.dto.ChatDto.RoomList;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatRoomResponseDto extends ErrorResponseBody {

    List<RoomList> roomLists;

    public static ChatRoomResponseDto of(Integer statusCode, Boolean success, String message, List<RoomList> roomList){
        ChatRoomResponseDto res = new ChatRoomResponseDto();
        res.setStatusCode(statusCode);
        res.setSuccess(success);
        res.setMessage(message);
        res.setRoomLists(roomList);
        return res;
    }
}
