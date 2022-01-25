package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.common.BaseResponseBody;
import com.ssafy.ssafymate.dto.ChatDto.RoomList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("ChatRoomResponseDto")
public class ChatRoomResponseDto{

    @ApiModelProperty(name="채팅방 리스트", example = "roomLists: []")
    List<RoomList> roomList;

    public static ChatRoomResponseDto of(List<RoomList> roomList){
        ChatRoomResponseDto res = new ChatRoomResponseDto();
        res.setRoomList(roomList);
        return res;
    }
}
