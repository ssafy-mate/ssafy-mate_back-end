package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.dto.TeamDto.TeamDetailOwnerDto;
import com.ssafy.ssafymate.dto.TeamDto.TeamDetailUserDto;
import com.ssafy.ssafymate.dto.TeamDto.TeamStackDto;
import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.UserTeam;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TeamModifyResponseDto {
    private Long teamId;
    private String teamName;
    private String teamImgUrl;
    private String campus;
    private String project;
    private String projectTrack;
    private String notice;
    private String introduction;
    List<TeamStackDto> techStacks = new ArrayList<>();
    private Integer totalRecruitment;
    private Integer frontendRecruitment;
    private Integer backendRecruitment;
    Integer totalHeadcount;
    Integer frontendHeadcount;
    Integer backendHeadcount;

    public static TeamModifyResponseDto of(Team team){
        TeamModifyResponseDto res = new TeamModifyResponseDto();
        res.setTeamId(team.getId());
        res.setTeamName(team.getTeamName());
        res.setTeamImgUrl(team.getTeamImg());
        res.setCampus(team.getCampus());
        res.setProject(team.getProject());
        res.setProjectTrack(team.getProjectTrack());
        res.setNotice(team.getNotice());
        res.setIntroduction(team.getIntroduction());
        res.setTechStacks(TeamStackDto.of(team.getTechStacks()));

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
        res.setTotalRecruitment(team.getTotalRecruitment());
        res.setFrontendRecruitment(team.getFrontendRecruitment());
        res.setBackendRecruitment(team.getBackendRecruitment());
        res.setTotalHeadcount(front+back);
        res.setFrontendHeadcount(front);
        res.setBackendHeadcount(back);
        return res;
    }
}
