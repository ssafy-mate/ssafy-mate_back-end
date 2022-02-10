package com.ssafy.ssafymate.dto.TeamDto;

import com.ssafy.ssafymate.dto.response.TeamModifyResponseDto;
import com.ssafy.ssafymate.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamDetail extends TeamModifyResponseDto {

    TeamDetailOwnerDto owner;
    List<TeamDetailUserDto> members = new ArrayList<>();
    private LocalDateTime createDateTime;

    public static TeamDetail of(Team team){
        return new TeamDetail(team);
    }

    public TeamDetail(Team team){
        super(team);
        this.setMembers(TeamDetailUserDto.of(team.getMembers()));
        this.setOwner(TeamDetailOwnerDto.of(team.getOwner()));
        this.setCreateDateTime(team.getCreateDateTime());
    }
}
