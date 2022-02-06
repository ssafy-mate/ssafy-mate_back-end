package com.ssafy.ssafymate.dto.UserDto;

import com.ssafy.ssafymate.entity.TechStack;
import com.ssafy.ssafymate.entity.UserStack;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserStackDto {

    Long id;
    String techStackName;
    String techStackImgUrl;
    String techStackLevel;

    public static List<UserStackDto> of(List<UserStack> userStacks){
        List<UserStackDto> res = new ArrayList<>();
        for (UserStack userStack : userStacks){
            TechStack techStack = userStack.getTechStack();
            UserStackDto teamStackDto = new UserStackDto();
            teamStackDto.setId(techStack.getId());
            teamStackDto.setTechStackName(techStack.getTechStackName());
            teamStackDto.setTechStackImgUrl(techStack.getTechStackImgUrl());
            teamStackDto.setTechStackLevel(userStack.getTechStackLevel());
            res.add(teamStackDto);
        }
        return  res;
    }
}
