package com.ssafy.ssafymate.dto.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageTeamRequestDto {

    @ApiModelProperty(value = "프로젝트", example = "특화 프로젝트")
    private String project;

    @ApiModelProperty(value = "받는 교육생 아이디", example = "2")
    private Long userId;

    @ApiModelProperty(value = "메세지", example = " 같이 하고 싶습니다. ")
    private String message;

}
