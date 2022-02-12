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

    @ApiModelProperty(name = "채팅방의 최신 내용", example = "마지막 글입니다.")
    private String content;

    @ApiModelProperty(name = "상대방의 이름", example = "조길동")
    private String userName;

    @ApiModelProperty(name = "상대방 유저 고유 아이디", example = "56")
    private Long userId;

    @ApiModelProperty(name = "마지막 채팅 시간", example = "2022-02-10T~~~")
    private String sentTime;

    @ApiModelProperty(name = "상대방 이메일", example = "asd123@naver.com")
    private String userEmail;

    @ApiModelProperty(name = "상대방 프로필 이미지", example = "https://asd~~~~")
    private String profileImgUrl;

    @ApiModelProperty(name = "채팅방 고유 인덱스", example = "56-68")
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
