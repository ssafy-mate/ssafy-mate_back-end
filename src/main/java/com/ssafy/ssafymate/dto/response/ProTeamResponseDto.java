package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.dto.ProDto.ProTeamDto;
import com.ssafy.ssafymate.entity.Team;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProTeamResponseDto {

    @ApiModelProperty(value = "팀 현황 파악" , example = "[]")
    List<ProTeamDto> teams;

    public static ProTeamResponseDto of(List<Team> teams){

        ProTeamResponseDto res = new ProTeamResponseDto();
        res.setTeams(ProTeamDto.of(teams));
        return res;

    }

}
