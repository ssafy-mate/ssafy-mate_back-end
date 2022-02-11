package com.ssafy.ssafymate.dto.ProDto;

import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProTeamDto {
    private String campus;
    private String projectTrack;
    private String teamName;
    private String ownerStudentNumber;
    private String ownerName;
    List<ProMemberDto> members;

    public static List<ProTeamDto> of(List<Team> teams) {
        List<ProTeamDto> res = new ArrayList<>();

        for (Team team : teams) {
            ProTeamDto proTeamDto = new ProTeamDto();
            proTeamDto.setCampus(team.getCampus());
            proTeamDto.setProjectTrack(team.getProjectTrack());
            proTeamDto.setTeamName(team.getTeamName());

            User owner = team.getOwner();
            proTeamDto.setOwnerStudentNumber(owner.getStudentNumber());
            proTeamDto.setOwnerName(owner.getStudentName());

            proTeamDto.setMembers(ProMemberDto.of(team.getMembers(),owner.getStudentNumber()));
            res.add(proTeamDto);
        }

        return res;

    }
}
