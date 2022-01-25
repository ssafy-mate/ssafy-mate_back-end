package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.common.BaseResponseBody;
import com.ssafy.ssafymate.dto.ChatDto.ContentList;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class ChatHistoryResponseDto{

    List<ContentList> contentList;

    public static ChatHistoryResponseDto of(List<ContentList> contentList){
        ChatHistoryResponseDto res = new ChatHistoryResponseDto();
        res.setContentList(contentList);
        return res;
    }
}
