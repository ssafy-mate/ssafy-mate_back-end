package com.ssafy.ssafymate.dto.TeamDto;

import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.TeamStack;
import com.ssafy.ssafymate.entity.UserTeam;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TeamDetail {
    private Long teamId;
    TeamDetailOwnerDto owner;
    private String teamImgUrl;
    private String campus;
    private String project;
    private String projectTrack;
    private String teamName;
    private String notice;
    private String introduction;
    List<TeamStackDto> techStacks = new ArrayList<>();
    List<TeamDetailUserDto> members = new ArrayList<>();
    private Integer totalRecruitment;
    private Integer frontendRecruitment;
    private Integer backendRecruitment;
    Integer totalHeadcount;
    Integer frontendHeadcount;
    Integer backendHeadcount;
    private LocalDateTime createDateTime;

    public static TeamDetail of(Team team){
        TeamDetail res = new TeamDetail();
        res.setTeamId(team.getId());
        res.setCampus(team.getCampus());
        res.setProject(team.getProject());
        res.setProjectTrack(team.getProjectTrack());
        res.setTeamName(team.getTeamName());
        res.setNotice(team.getNotice());
        res.setIntroduction(team.getIntroduction());
        res.setTechStacks(TeamStackDto.of(team.getTechStacks()));
        res.setOwner(TeamDetailOwnerDto.of(team.getOwner()));
        res.setMembers(TeamDetailUserDto.of(team.getMembers()));

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

        res.setTeamImgUrl(team.getTeamImg());
        res.setTotalRecruitment(team.getTotalRecruitment());
        res.setFrontendRecruitment(team.getFrontendRecruitment());
        res.setBackendRecruitment(team.getBackendRecruitment());
        res.setTotalHeadcount(front+back);
        res.setFrontendHeadcount(front);
        res.setBackendHeadcount(back);
        return res;
    }
}
