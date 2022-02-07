package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.dto.ChatDto.RoomList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ApiModel("ChatRoomResponseDto")
public class ChatRoomResponseDto {

    private String content;

    private String userName;

    private Long userId;

    private String sentTime;

    private String userEmail;

    private String profileImgUrl;

    private String roomId;

    public static List<ChatRoomResponseDto> of(List<RoomList> roomList) {
        List<ChatRoomResponseDto> res = new ArrayList<>();

        for (RoomList rl : roomList) {
            ChatRoomResponseDto crrd = new ChatRoomResponseDto();

            crrd.setRoomId(rl.getRoom_id());
            crrd.setContent(rl.getContent());
            crrd.setSentTime(rl.getSent_time());
            crrd.setUserEmail(rl.getEmail());
            crrd.setUserId(rl.getUser_id());
            crrd.setProfileImgUrl(rl.getProfile_img());
            crrd.setUserName(rl.getUsername());

            res.add(crrd);
        }
        return res;
    }
}
