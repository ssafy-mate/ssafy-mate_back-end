package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.dto.UserDto.UserProjectLoginDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserProjectResponseDto {
    List<UserProjectLoginDto> projects;

    public static UserProjectResponseDto of(List<UserProjectLoginDto> user){
        UserProjectResponseDto res = new UserProjectResponseDto();
        res.setProjects(user);
        return res;
    }
}
