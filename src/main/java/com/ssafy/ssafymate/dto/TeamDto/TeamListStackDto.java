package com.ssafy.ssafymate.dto.TeamDto;

import com.ssafy.ssafymate.entity.TeamStack;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TeamListStackDto {
    private String techStackName;

    public static List<TeamListStackDto> of (List<TeamStack> teamStacks){
        List<TeamListStackDto> res = new ArrayList<>();

        for(TeamStack teamStack : teamStacks){
            TeamListStackDto teamListStackDto = new TeamListStackDto();
            teamListStackDto.setTechStackName(teamStack.getTechStack().getTechStackName());

            res.add(teamListStackDto);
        }

        return res;
    }
}
