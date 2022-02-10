package com.ssafy.ssafymate.dto.UserDto;

import com.ssafy.ssafymate.entity.UserStack;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserListStackDto {

    private Long techStackId;
    private String techStackName;

    public static List<UserListStackDto> of (List<UserStack> userStacks){
        List<UserListStackDto> res = new ArrayList<>();

        for(UserStack userStack : userStacks){
            UserListStackDto userListStackDto = new UserListStackDto();
            userListStackDto.setTechStackId(userStack.getTechStack().getTechStackId());
            userListStackDto.setTechStackName(userStack.getTechStack().getTechStackName());

            res.add(userListStackDto);
        }

        return res;
    }
}
