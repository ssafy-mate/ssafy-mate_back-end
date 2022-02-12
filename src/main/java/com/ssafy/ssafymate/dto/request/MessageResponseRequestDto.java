package com.ssafy.ssafymate.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "요청 응답", description = "제안 수락/거절/취소")
public class MessageResponseRequestDto {

    @ApiModelProperty(value = "요청 번호", example = "2")
    private Long requestId;

    @ApiModelProperty(value = "응답", example = "approval")
    private String response;

}
