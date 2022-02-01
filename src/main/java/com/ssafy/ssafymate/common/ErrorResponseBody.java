package com.ssafy.ssafymate.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ErrorResponseBody")
public class ErrorResponseBody{

    @ApiModelProperty(name="응답 메시지", example = "정상")
    String message = null;
    @ApiModelProperty(name="응답 코드", example = "200")
    Integer status = null;
    @ApiModelProperty(name="응답 상태", example = "true")
    Boolean success = false;

    public ErrorResponseBody() {}

    public ErrorResponseBody(Integer statusCode){
        this.status = statusCode;
    }

    public ErrorResponseBody(Integer statusCode, String message) {
        this.status = statusCode;
        this.message = message;
    }

    public static ErrorResponseBody of(Integer statusCode, Boolean success, String message) {
        ErrorResponseBody body = new ErrorResponseBody();
        body.message = message;
        body.status = statusCode;
        body.success = success;
        return body;
    }
}
