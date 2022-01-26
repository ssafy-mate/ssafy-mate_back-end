package com.ssafy.ssafymate.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("LoginResponseDto")
@Getter
@Setter
public class LoginResponseDto{

    @ApiModelProperty(name="access-token", example = "akslndasujndoiw.asdfnjdaifnklfegsg2134.fsdfsadfsd")
    private String token;

    @ApiModelProperty(name="message", example = "로그인 하였습니다.")
    private String messgae;

    public static LoginResponseDto of(String message, String token) {
        LoginResponseDto body = new LoginResponseDto();
        body.setMessgae(message);
        body.setToken(token);
        return body;
    }
}
