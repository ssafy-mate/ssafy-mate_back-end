package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.common.BaseResponseBody;
import com.ssafy.ssafymate.dto.ChatDto.ContentList;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatHistoryResponseDto extends BaseResponseBody {

    List<ContentList> contentList;

    public static ChatHistoryResponseDto of(Integer statusCode, Boolean success, String message, List<ContentList> contentList){
        ChatHistoryResponseDto res = new ChatHistoryResponseDto();
        res.setStatusCode(statusCode);
        res.setSuccess(success);
        res.setMessage(message);
        res.setContentList(contentList);
        return res;
    }
}
