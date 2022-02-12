package com.ssafy.ssafymate.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSelectProjectTrackRequestDto {

    @ApiModelProperty(value = "프로젝트", example = "특화 프로젝트")
    private String project;

    @ApiModelProperty(value = "프로젝트 트랙", example = "빅데이터(분산)")
    private String projectTrack;

}
