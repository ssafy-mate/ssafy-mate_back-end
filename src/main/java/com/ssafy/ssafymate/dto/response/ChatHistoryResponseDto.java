package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.dto.ChatDto.ContentList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.swing.text.AbstractDocument;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ApiModel("ChatHistoryResponseDto")
public class ChatHistoryResponseDto {

    private Long id;

    private String roomId;

    private String content;

    private String sentTime;

    private String userName;

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
