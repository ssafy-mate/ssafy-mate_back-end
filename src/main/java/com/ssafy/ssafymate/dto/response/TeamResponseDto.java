package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.common.BaseResponseBody;
import com.ssafy.ssafymate.entity.Team;
import lombok.Setter;


@Setter
public class TeamResponseDto extends BaseResponseBody {
    Team teamData;

    public static TeamResponseDto of(Integer statusCode, Boolean success, String message,Team team){
        TeamResponseDto res = new TeamResponseDto();
        res.setStatusCode(statusCode);
        res.setSuccess(success);
        res.setMessage(message);
        res.setTeamData(team);
        return res;
    }
}
