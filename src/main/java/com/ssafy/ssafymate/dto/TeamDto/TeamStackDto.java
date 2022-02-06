package com.ssafy.ssafymate.dto.TeamDto;

import com.ssafy.ssafymate.entity.TeamStack;
import com.ssafy.ssafymate.entity.TechStack;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TeamStackDto {

    Long id;
    String techStackName;
    String techStackImgUrl;

    public static List<TeamStackDto> of(List<TeamStack> teamStacks){
        List<TeamStackDto> res = new ArrayList<>();
        for (TeamStack teamStack : teamStacks){
            TechStack techStack = teamStack.getTechStack();
            TeamStackDto teamStackDto = new TeamStackDto();
            teamStackDto.setId(techStack.getId());
            teamStackDto.setTechStackName(techStack.getTechStackName());
            teamStackDto.setTechStackImgUrl(techStack.getTechStackImgUrl());
            res.add(teamStackDto);
        }
        return  res;
    }
}
