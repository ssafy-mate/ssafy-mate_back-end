package com.ssafy.ssafymate.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class TeamListRequestDto {

    @ApiModelProperty(value = "캠퍼스", example = "서울")
    private String campus="";

    @ApiModelProperty(value = "프로젝트", example = "특화 프로젝트")
    private String project="";

    @ApiModelProperty(value = "프로젝트 트랙", example = "IoT")
    private String project_track="";

    @ApiModelProperty(value = "기술 스택")
    private String techstack_id;

    @ApiModelProperty(value = "팀 이름", example = "우수상")
    private String team_name="";

    @ApiModelProperty(value = "희망 직무", example = "프론트엔드(Front-end)")
    private String job1="";

    @ApiModelProperty(value = "정렬", example = "최신순")
    private String sort="";

    @ApiModelProperty(value = "가입 가능 팀", example = "false")
    private Boolean exclusion=false;

}
