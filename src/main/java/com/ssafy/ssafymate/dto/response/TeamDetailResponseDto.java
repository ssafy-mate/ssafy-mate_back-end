package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.dto.TeamDto.TeamDetail;
import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.entity.UserTeam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamDetailResponseDto {
    @ApiModelProperty(name="팀 상세정보", example = "team :{}")
    TeamDetail teamData;

    String role;

    public static TeamDetailResponseDto of(Team team, User user){
        TeamDetailResponseDto res = new TeamDetailResponseDto();

        if(team == null){
            res.setTeamData(null);
            res.setRole(null);
        }
        else {
            res.setTeamData(TeamDetail.of(team));
            res.setRole("outsider");
            if (team.getOwner().getId().equals(user.getId()))
                res.setRole("owner");
            else {
                for (UserTeam member : team.getMembers()) {
                    if (member.getUser().getId().equals(user.getId())) {
                        res.setRole("member");
                        break;
                    }
                }
            }
        }
        return res;
    }
}
