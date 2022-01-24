package com.ssafy.ssafymate.dto.TeamDto;

import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.entity.UserTeam;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class TeamDetailUserDto {
    private Long userId;
    private String userName;
    private String profileImgUrl;
    private String ssafyTrack;
    private String job1;

    public static List<TeamDetailUserDto> of(List<UserTeam> users){
        List<TeamDetailUserDto> res = new ArrayList<>();
        for(UserTeam userteam : users) {
            User user = userteam.getUser();
            TeamDetailUserDto teamDetailUserDto = new TeamDetailUserDto();
            teamDetailUserDto.setUserId(user.getId());
            teamDetailUserDto.setUserName(user.getStudentName());
            teamDetailUserDto.setProfileImgUrl(user.getProfileImg());
            teamDetailUserDto.setSsafyTrack(user.getSsafyTrack());
            teamDetailUserDto.setJob1(user.getJob1());
            res.add(teamDetailUserDto);
        }
        return res;
    }
}
