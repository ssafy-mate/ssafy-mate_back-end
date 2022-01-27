package com.ssafy.ssafymate.dto.TeamDto;

import com.ssafy.ssafymate.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamDetailOwnerDto {
    private Long userId;
    private String userName;

    public static TeamDetailOwnerDto of(User user){
        TeamDetailOwnerDto res = new TeamDetailOwnerDto();
        res.setUserId(user.getId());
        res.setUserName(user.getStudentName());
        return res;
    }
}
