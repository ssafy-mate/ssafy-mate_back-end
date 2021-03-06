package com.ssafy.ssafymate.dto.TeamDto;

import com.ssafy.ssafymate.entity.Team;
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
    private String project;
    private String projectTrack;
    private String teamName;
    private String notice;
    List<TeamListStackDto> techStacks = new ArrayList<>();
    private Integer totalRecruitment;
    private Integer frontendRecruitment;
    private Integer backendRecruitment;
    Integer totalHeadcount;
    Integer frontendHeadcount;
    Integer backendHeadcount;
    private LocalDateTime createDateTime;
    Boolean isRecruiting = false;

    public static List<TeamBoardDto> of (List<Team> teams){
        List<TeamBoardDto> res = new ArrayList<>();

        for(Team team : teams){
            TeamBoardDto teamBoardDto = new TeamBoardDto();

            teamBoardDto.setTeamId(team.getId());
            teamBoardDto.setTeamName(team.getTeamName());
            teamBoardDto.setTeamImgUrl(team.getTeamImg());
            teamBoardDto.setCampus(team.getCampus());
            teamBoardDto.setProject(team.getProject());
            teamBoardDto.setProjectTrack(team.getProjectTrack());
            teamBoardDto.setNotice(team.getNotice());
            teamBoardDto.setTechStacks(TeamListStackDto.of(team.getTechStacks()));
            teamBoardDto.setTotalRecruitment(team.getTotalRecruitment());
            teamBoardDto.setFrontendRecruitment(team.getFrontendRecruitment());
            teamBoardDto.setBackendRecruitment(team.getBackendRecruitment());

            teamBoardDto.setTotalHeadcount(team.getTotalHeadcount());
            teamBoardDto.setFrontendHeadcount(team.getFrontendHeadcount());
            teamBoardDto.setBackendHeadcount(team.getBackendHeadcount());

            teamBoardDto.setCreateDateTime(team.getCreateDateTime());
            if(team.getTotalRecruitment() > team.getTotalHeadcount()){
                teamBoardDto.setIsRecruiting(true);
            }


            res.add(teamBoardDto);
        }
        return res;
    }
}
