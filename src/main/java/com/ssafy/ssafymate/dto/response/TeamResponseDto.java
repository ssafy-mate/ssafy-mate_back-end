package com.ssafy.ssafymate.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamResponseDto {
    private Long teamId;

    private String message;

    public static TeamResponseDto of(Long teamId, String message){
        TeamResponseDto res = new TeamResponseDto();
        res.setTeamId(teamId);
        res.setMessage(message);
        return res;
    }
}
