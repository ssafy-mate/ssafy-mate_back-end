package com.ssafy.ssafymate.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamResponseDto {

    @ApiModelProperty(name="팀 아이디", example = "2")
    private Long teamId;

    @ApiModelProperty(name="성공 메세지", example = "팀을 성공적으로 생성하였습니다.")
    private String message;

    public static TeamResponseDto of(Long teamId, String message){
        TeamResponseDto res = new TeamResponseDto();
        res.setTeamId(teamId);
        res.setMessage(message);
        return res;
    }
}
