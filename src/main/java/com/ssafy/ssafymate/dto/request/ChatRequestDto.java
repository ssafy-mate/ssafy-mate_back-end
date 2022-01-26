package com.ssafy.ssafymate.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "대화 내용 요청 클래스", description = "상대방 아이디와 사용자 아이디를 담는 클래스")
public class ChatRequestDto {

    @ApiModelProperty(value = "아이디1", example = "123")
    private Long userId1;

    @ApiModelProperty(value = "아이디2", example = "124")
    private Long userId2;
}
