package com.ssafy.ssafymate.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("Successbody")
public class SuccessBody {

    @ApiModelProperty(name="Success", example = "true")
    Boolean success = false;

    public static SuccessBody of(Boolean success) {
        SuccessBody res = new SuccessBody();
        res.setSuccess(success);
        return res;
    }
}
