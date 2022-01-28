package com.ssafy.ssafymate.dto.request;

import com.ssafy.ssafymate.entity.TeamStack;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeamRequestDto {

    @ApiModelProperty(value = "캠퍼스", example = "서울")
    private String campus;

    @ApiModelProperty(value = "프로젝트", example = "특화 프로젝트")
    private String project;

    @ApiModelProperty(value = "프로젝트 트랙", example = "IoT")
    private String projectTrack;

    @ApiModelProperty(value = "팀 이름", example = "팀 싸피")
    private String teamName;

    @ApiModelProperty(value = "팀 공고 문구", example = "최우수상에 도전할 팀원을 모집합니다.")
    private String notice;

    @ApiModelProperty(value = "팀 소개", example = "저희 팀은 ...")
    private String introduction;

    @ApiModelProperty(value = "기술 스택")
    List<TeamStack> techStacks = new ArrayList<>();

    @ApiModelProperty(value = "전체 모집 인원", example = "6")
    private int totalRecruitment;

    @ApiModelProperty(value = "프론트엔드 모집 인원", example = "3")
    private int frontendRecruitment;

    @ApiModelProperty(value = "백엔드 모집 인원", example = "3")
    private int backendRecruitment;
}
