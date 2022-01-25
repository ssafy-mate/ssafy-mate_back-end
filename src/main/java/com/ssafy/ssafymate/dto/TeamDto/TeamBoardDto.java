package com.ssafy.ssafymate.dto.TeamDto;

import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.TeamStack;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TeamBoardDto {

    private Long teamId;
    private String teamImgUrl;
    private String campus;
    private String teamName;
    private String notice;
    List<TeamStack> techStacks = new ArrayList<>();
    private Integer totalRecruitment;
    private Integer frontendRecruitment;
    private Integer backendRecruitment;
    Integer totalHeadcount;
    Integer frontendHeadcount;
    Integer backendHeadcount;
    private LocalDateTime createDateTime;

    public static List<TeamBoardDto> of (List<Team> teams){
        List<TeamBoardDto> res = new ArrayList<>();

        for(Team team : teams){
            TeamBoardDto teamBoardDto = new TeamBoardDto();

            teamBoardDto.setTeamId(team.getId());
            teamBoardDto.setTeamName(team.getTeamName());
            teamBoardDto.setTeamImgUrl(team.getTeamImg());
            teamBoardDto.setCampus(team.getCampus());
            teamBoardDto.setNotice(team.getNotice());
            teamBoardDto.setTechStacks(team.getTechStacks());
            teamBoardDto.setTotalRecruitment(team.getTotalRecruitment());
            teamBoardDto.setFrontendRecruitment(team.getFrontendRecruitment());
            teamBoardDto.setBackendRecruitment(team.getBackendRecruitment());
            teamBoardDto.setTotalHeadcount(team.getTotalHeadcount());
            teamBoardDto.setFrontendHeadcount(team.getFrontendHeadcount());
            teamBoardDto.setBackendHeadcount(team.getBackendHeadcount());

            res.add(teamBoardDto);
        }
        return res;
    }
}
