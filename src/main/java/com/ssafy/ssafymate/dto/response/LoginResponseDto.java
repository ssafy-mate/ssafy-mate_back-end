package com.ssafy.ssafymate.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("LoginResponseDto")
@Getter
@Setter
public class LoginResponseDto{

    @ApiModelProperty(name="응답 메시지", example = "정상")
    String message = null;
    @ApiModelProperty(name="응답 코드", example = "200")
    Integer statusCode = null;
    @ApiModelProperty(name="응답 상태", example = "true")
    Boolean success = false;
    @ApiModelProperty(name="access-token", example = "akslndasujndoiw.asdfnjdaifnklfegsg2134.fsdfsadfsd")
    private String token;

    public static LoginResponseDto of(Integer statusCode, Boolean success, String message, String token) {
        LoginResponseDto body = new LoginResponseDto();
        body.message = message;
        body.statusCode = statusCode;
        body.success = success;
        body.token = "Bearer " + token;
        return body;
    }
}
