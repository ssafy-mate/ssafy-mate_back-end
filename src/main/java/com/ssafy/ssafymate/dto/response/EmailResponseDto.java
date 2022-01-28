package com.ssafy.ssafymate.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("EmailResponseDto")
@Getter
@Setter
public class EmailResponseDto {

    @ApiModelProperty(name="응답 메시지", example = "정상")
    String message = null;
    @ApiModelProperty(name="응답 코드", example = "200")
    Integer statusCode = null;
    @ApiModelProperty(name="응답 상태", example = "true")
    Boolean success = false;
    @ApiModelProperty(name="아이디", example = "ssafymate@gmail.com")
    private String email;

    public static EmailResponseDto of(Integer statusCode, Boolean success, String message, String email) {
        EmailResponseDto body = new EmailResponseDto();
        body.message = message;
        body.statusCode = statusCode;
        body.success = success;
        body.email = email;
        return body;
    }
}