package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.dto.UserDto.UserDetailDto;
import com.ssafy.ssafymate.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    @ApiModelProperty(name="교육생 상세정보", example = "user :{}")
    UserDetailDto userData;

    public static UserResponseDto of(User user){

        UserResponseDto res = new UserResponseDto();
        res.setUserData(UserDetailDto.of(user));
        return res;
    }

}
