package com.ssafy.ssafymate.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@ApiModel("MessageBody")
public class MessageBody {

    @ApiModelProperty(name="응답 메시지", example = "정상")
    String message = null;

    public static MessageBody of(String message) {
        MessageBody res = new MessageBody();
        res.setMessage(message);
        return res;
    }
}
