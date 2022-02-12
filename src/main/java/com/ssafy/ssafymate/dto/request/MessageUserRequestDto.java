package com.ssafy.ssafymate.dto.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageUserRequestDto {

    @ApiModelProperty(value = "팀 아이디", example = "1")
    private Long teamId;

    @ApiModelProperty(value = "메세지", example = " 참여하고 싶습니다. ")
    private String message;

}
