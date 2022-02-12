package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.dto.ChatDto.ContentList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ApiModel("ChatHistoryResponseDto")
public class ChatHistoryResponseDto {

    @ApiModelProperty(name = "채팅 인덱스", example = "12")
    private Long id;

    @ApiModelProperty(name = "채팅방 인덱스", example = "56-68")
    private String roomId;

    @ApiModelProperty(name = "채팅 내용", example = "안녕하세요.")
    private String content;

    @ApiModelProperty(name = "채팅 보낸 시간", example = "2022-02-10T~~")
    private String sentTime;

    @ApiModelProperty(name = "채팅 보낸 사람 이름", example = "김길동")
    private String userName;

    @ApiModelProperty(name = "채팅 보낸 사람 고유 아이디", example = "56")
    private Long senderId;

    public static List<ChatHistoryResponseDto> of(List<ContentList> contentList) {
        List<ChatHistoryResponseDto> res = new ArrayList<>();

        for (ContentList cl : contentList) {
            ChatHistoryResponseDto chr = new ChatHistoryResponseDto();

            chr.setId(cl.getId());
            chr.setRoomId(cl.getRoom_id());
            chr.setContent(cl.getContent());
            chr.setSentTime(cl.getSent_time());
            chr.setSenderId(cl.getSender_id());
            chr.setUserName(cl.getUsername());

            res.add(chr);
        }

        return res;

    }

}
