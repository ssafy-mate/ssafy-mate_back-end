package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.dto.ChatDto.ContentList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("ChatHistoryTotalPagesResponseDto")
public class ChatHistoryCursorPagingResponseDto {

    @ApiModelProperty(name = "대화내용 리스트", example = "contentLists: []")
    List<ChatHistoryResponseDto> contentList;

    @ApiModelProperty(name = "마지막 인덱스", example = "56")
    Long nextCursor;

    public static ChatHistoryCursorPagingResponseDto of(List<ContentList> contentList) {

        ChatHistoryCursorPagingResponseDto res = new ChatHistoryCursorPagingResponseDto();
        res.setContentList(ChatHistoryResponseDto.of(contentList));

        int lastIndex = contentList.size();
        Long nextCursor = 0L;
        if(lastIndex != 0){
            nextCursor = contentList.get(lastIndex-1).getId();
        }
        res.setNextCursor(nextCursor);

        return res;

    }

}
