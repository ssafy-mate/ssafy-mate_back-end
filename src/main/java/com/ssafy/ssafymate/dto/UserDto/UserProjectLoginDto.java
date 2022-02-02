package com.ssafy.ssafymate.dto.UserDto;

import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.entity.UserTeam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserProjectLoginDto {
    private Long id;
    private String name;
    private String projectTrack;
    private Long projectTeamId;

    public static List<UserProjectLoginDto> of(List<UserTeam> teams , User user){
        List<UserProjectLoginDto> res = new ArrayList<>();
        UserProjectLoginDto commonProjectTrack = new UserProjectLoginDto(1L,"공통 프로젝트",null,null);
        UserProjectLoginDto specializationProjectTrack = new UserProjectLoginDto(2L,"특화 프로젝트",null,null);
        UserProjectLoginDto autonomyProjectTrack = new UserProjectLoginDto(3L,"자율 프로젝트",null,null);

        commonProjectTrack.setProjectTrack(user.getCommonProjectTrack());
        specializationProjectTrack.setProjectTrack(user.getSpecializationProjectTrack());
        for(UserTeam userTeam : teams){
            Team team = userTeam.getTeam();
            switch (team.getProject()){
                case "공통 프로젝트":
                    commonProjectTrack.setProjectTeamId(team.getId());
                    break;
                case "특화 프로젝트":
                    specializationProjectTrack.setProjectTeamId(team.getId());
                    break;
                case "자율 프로젝트":
                    autonomyProjectTrack.setProjectTeamId(team.getId());
                    break;
            }
        }
        res.add(commonProjectTrack);
        res.add(specializationProjectTrack);
        res.add(autonomyProjectTrack);

        return res;
    }
}
