package com.ssafy.ssafymate.dto.request;

import com.ssafy.ssafymate.entity.TeamStack;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TeamListReuestDto {
    @ApiModelProperty(value = "프로젝트", example = "특화 프로젝트")
    private String project;

    @ApiModelProperty(value = "프로젝트 트랙", example = "IoT")
    private String projectTrack;

    @ApiModelProperty(value = "기술 스택")
    List<TeamStack> techStacks = new ArrayList<>();

}
