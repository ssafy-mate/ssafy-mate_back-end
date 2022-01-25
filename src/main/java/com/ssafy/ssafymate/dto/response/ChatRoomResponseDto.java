package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.common.BaseResponseBody;
import com.ssafy.ssafymate.dto.ChatDto.RoomList;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatRoomResponseDto{

    List<RoomList> roomLists;

    public static ChatRoomResponseDto of(List<RoomList> roomList){
        ChatRoomResponseDto res = new ChatRoomResponseDto();
        res.setRoomLists(roomList);
        return res;
    }
}
