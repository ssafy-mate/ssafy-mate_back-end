package com.ssafy.ssafymate.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTeamIdResponseDto {

    @ApiModelProperty(name="팀 아이디", example = "1")
    private Long teamId;

    public static UserTeamIdResponseDto of(Long id){

        UserTeamIdResponseDto res = new UserTeamIdResponseDto();
        res.setTeamId(id);
        return res;

    }

}
