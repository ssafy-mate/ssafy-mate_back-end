package com.ssafy.ssafymate.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;


@ToString
@Getter
@Setter
public class UserListRequestDto {

    @ApiModelProperty(value = "캠퍼스", example = "서울")
    private String campus = "";

    @ApiModelProperty(value = "싸피 트랙", example = "Java 트랙")
    private String ssafy_track= "";

    @ApiModelProperty(value = "프로젝트", example = "특화 프로젝트")
    private String project= "";

    @ApiModelProperty(value = "프로젝트 트랙", example = "IoT")
    private String project_track= "";

    @ApiModelProperty(value = "희망 직무", example = "프론트엔드(Front-end)")
    private String job1= "";

    @ApiModelProperty(value = "이름", example = "김싸피")
    private String user_name= "";

    @ApiModelProperty(value = "기술 스택")
    private String tech_stacks="0";

    @ApiModelProperty(value = "정렬", example = "최신순")
    private String sort= "";

    @ApiModelProperty(value = "초대 가능 교유생", example = "true")
    private Boolean exclusion=false;

}
