package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.common.BaseResponseBody;
import com.ssafy.ssafymate.entity.Team;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("TeamList")
public class TeamListResponseDto extends BaseResponseBody {
    List<Team> teams;

    public static TeamListResponseDto of(Integer statusCode, Boolean success, String message, List<Team> teams){
        TeamListResponseDto res = new TeamListResponseDto();
        res.setStatusCode(statusCode);
        res.setSuccess(success);
        res.setMessage(message);
        res.setTeams(teams);
        return res;
    }
}
