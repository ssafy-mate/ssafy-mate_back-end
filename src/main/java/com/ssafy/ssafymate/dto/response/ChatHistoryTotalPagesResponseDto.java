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
public class ChatHistoryTotalPagesResponseDto {

    @ApiModelProperty(name = "대화내용 리스트", example = "contentLists: []")
    List<ChatHistoryResponseDto> contentList;

    @ApiModelProperty(name = "전체 페이지 수", example = "3")
    int totalPages;

    public static ChatHistoryTotalPagesResponseDto of(List<ContentList> contentList, int totalPages) {
        ChatHistoryTotalPagesResponseDto res = new ChatHistoryTotalPagesResponseDto();
        res.setContentList(ChatHistoryResponseDto.of(contentList));
        res.setTotalPages(totalPages);
        return res;
    }
}
