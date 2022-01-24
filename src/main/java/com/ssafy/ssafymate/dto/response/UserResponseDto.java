package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.common.BaseResponseBody;
import com.ssafy.ssafymate.dto.UserDto.UserDetailDto;
import com.ssafy.ssafymate.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto extends BaseResponseBody {
    @ApiModelProperty(name="교육생 상세정보", example = "user :{}")
    UserDetailDto userData;

    public static UserResponseDto of(Integer statusCode, Boolean success, String message, User user){
        UserResponseDto res = new UserResponseDto();
        res.setStatusCode(statusCode);
        res.setSuccess(success);
        res.setMessage(message);
        res.setUserData(UserDetailDto.of(user));
        return res;
    }
}
