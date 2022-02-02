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
public class UserProjectDto {
    private Long id;
    private String name;
    private String projectTrack;
    private UserProjectTeamDto projectTeam;

    public static List<UserProjectDto> of(List<UserTeam> teams , User user){
        List<UserProjectDto> res = new ArrayList<>();
        UserProjectDto commonProjectTrack = new UserProjectDto(1L,"공통 프로젝트",null,null);
        UserProjectDto specializationProjectTrack = new UserProjectDto(2L,"특화 프로젝트",null,null);
        UserProjectDto autonomyProjectTrack = new UserProjectDto(3L,"자율 프로젝트",null,null);

        commonProjectTrack.setProjectTrack(user.getCommonProjectTrack());
        specializationProjectTrack.setProjectTrack(user.getSpecializationProjectTrack());
        for(UserTeam userTeam : teams){
            Team team = userTeam.getTeam();
            switch (team.getProject()){
                case "공통 프로젝트":
                    commonProjectTrack.setProjectTeam(new UserProjectTeamDto(team.getId(),team.getTeamName()));
                    break;
                case "특화 프로젝트":
                    specializationProjectTrack.setProjectTeam(new UserProjectTeamDto(team.getId(),team.getTeamName()));
                    break;
                case "자율 프로젝트":
                    autonomyProjectTrack.setProjectTeam(new UserProjectTeamDto(team.getId(),team.getTeamName()));
                    break;
            }
        }
        res.add(commonProjectTrack);
        res.add(specializationProjectTrack);
        res.add(autonomyProjectTrack);

        return res;
    }
}
