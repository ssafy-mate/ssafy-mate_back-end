package com.ssafy.ssafymate.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BelongToTeam  {

    @ApiModelProperty(value = "팀 참여 가능 여부", example = "true")
    private Boolean belongToTeam;

    public static BelongToTeam of(Boolean belongToTeam){

        BelongToTeam res = new BelongToTeam();
        res.setBelongToTeam(belongToTeam);
        return res;

    }

}
