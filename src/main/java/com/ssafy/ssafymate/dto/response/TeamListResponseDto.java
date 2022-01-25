package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.common.BaseResponseBody;
import com.ssafy.ssafymate.dto.TeamDto.TeamBoardDto;
import com.ssafy.ssafymate.entity.Team;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("TeamList")
public class TeamListResponseDto extends BaseResponseBody {
    @ApiModelProperty(name="팀 리스트", example = "teams :[]")
    List<TeamBoardDto> teams;

    public static TeamListResponseDto of(Integer statusCode, Boolean success, String message, List<Team> teams){
        TeamListResponseDto res = new TeamListResponseDto();
        res.setStatusCode(statusCode);
        res.setSuccess(success);
        res.setMessage(message);
        res.setTeams(TeamBoardDto.of(teams));
        return res;
    }
}
