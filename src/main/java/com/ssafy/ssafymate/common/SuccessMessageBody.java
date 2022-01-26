package com.ssafy.ssafymate.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("SuccessMessageBody")
public class SuccessMessageBody extends MessageBody{
    @ApiModelProperty(name="응답 상태", example = "true")
    Boolean success = false;


    public static SuccessMessageBody of(Boolean success, String message){
        SuccessMessageBody res = new SuccessMessageBody();
        res.setSuccess(success);
        res.setMessage(message);

        return res;
    }
}
