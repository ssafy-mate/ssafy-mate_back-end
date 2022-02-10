package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.dto.TeamDto.TeamStackDto;
import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.UserTeam;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeamModifyResponseDto {
    protected Long teamId;
    protected String teamName;
    protected String teamImgUrl;
    protected String campus;
    protected String project;
    protected String projectTrack;
    protected String notice;
    protected String introduction;
    List<TeamStackDto> techStacks = new ArrayList<>();
    protected Integer totalRecruitment;
    protected Integer frontendRecruitment;
    protected Integer backendRecruitment;
    Integer totalHeadcount;
    Integer frontendHeadcount;
    Integer backendHeadcount;

    public static TeamModifyResponseDto of(Team team){
        return new TeamModifyResponseDto(team);
    }

    public TeamModifyResponseDto (Team team){
        this.setTeamId(team.getId());
        this.setTeamName(team.getTeamName());
        this.setTeamImgUrl(team.getTeamImg());
        this.setCampus(team.getCampus());
        this.setProject(team.getProject());
        this.setProjectTrack(team.getProjectTrack());
        this.setNotice(team.getNotice());
        this.setIntroduction(team.getIntroduction());
        this.setTechStacks(TeamStackDto.of(team.getTechStacks()));

        int front = 0;
        int back = 0;
        for (UserTeam userTeam : team.getMembers()){
            if(userTeam.getUser().getJob1().contains("Front")){
                front++;
            }
            else if(userTeam.getUser().getJob1().contains("Back")){
                back++;
            }
        }
        this.setTotalRecruitment(team.getTotalRecruitment());
        this.setFrontendRecruitment(team.getFrontendRecruitment());
        this.setBackendRecruitment(team.getBackendRecruitment());
        this.setTotalHeadcount(front+back);
        this.setFrontendHeadcount(front);
        this.setBackendHeadcount(back);
    }
}
