package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.dto.TeamDto.TeamDetail;
import com.ssafy.ssafymate.entity.Team;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamDetailResponseDto {
    @ApiModelProperty(name="팀 상세정보", example = "team :{}")
    TeamDetail teamData;

    public static TeamDetailResponseDto of(Team team){
        TeamDetailResponseDto res = new TeamDetailResponseDto();

        res.setTeamData(TeamDetail.of(team));
        return res;
    }
}
