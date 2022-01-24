package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.common.BaseResponseBody;
import com.ssafy.ssafymate.dto.TeamDto.TeamDetail;
import com.ssafy.ssafymate.entity.Team;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamResponseDto extends BaseResponseBody {
    @ApiModelProperty(name="팀 상세정보", example = "team :{}")
    TeamDetail teamData;

    public static TeamResponseDto of(Integer statusCode, Boolean success, String message,Team team){
        TeamResponseDto res = new TeamResponseDto();
        res.setStatusCode(statusCode);
        res.setSuccess(success);
        res.setMessage(message);
        res.setTeamData(TeamDetail.of(team));
        return res;
    }
}
