package com.ssafy.ssafymate.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTeamIdResponseDto {
    private Long projectId;

    public static UserTeamIdResponseDto of(Long id){
        UserTeamIdResponseDto res = new UserTeamIdResponseDto();
        res.setProjectId(id);
        return res;
    }
}
