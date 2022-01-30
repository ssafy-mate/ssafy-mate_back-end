package com.ssafy.ssafymate.dto.request;

import com.ssafy.ssafymate.entity.TeamStack;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
public class TeamListReuestDto {
    @ApiModelProperty(value = "캠퍼스", example = "서울")
    private String campus;

    @ApiModelProperty(value = "프로젝트", example = "특화 프로젝트")
    private String project;

    @ApiModelProperty(value = "프로젝트 트랙", example = "IoT")
    private String projectTrack;

    @ApiModelProperty(value = "기술 스택")
    private String techStacks;

    @ApiModelProperty(value = "팀 이름", example = "우수상")
    private String teamName;

    @ApiModelProperty(value = "희망 직무", example = "프론트엔드(Front-end)")
    private String job;

    @ApiModelProperty(value = "정렬", example = "최신순")
    private String sort;

}
