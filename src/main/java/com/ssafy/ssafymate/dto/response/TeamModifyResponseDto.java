package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.dto.TeamDto.TeamStackDto;
import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.UserTeam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeamModifyResponseDto {

    @ApiModelProperty(name="팀 아이디", example = "1")
    protected Long teamId;

    @ApiModelProperty(name="팀 이름", example = "팀 싸피")
    protected String teamName;

    @ApiModelProperty(name="팀 이미지 Url", example = "C:\\image\\team.jpg")
    protected String teamImgUrl;

    @ApiModelProperty(name="캠퍼스", example = "서울")
    protected String campus;

    @ApiModelProperty(name="프로젝트", example = "특화 프로젝트")
    protected String project;

    @ApiModelProperty(name="프로젝트 트랙", example = "빅데이터(분산)")
    protected String projectTrack;

    @ApiModelProperty(name="팀 공고 문구", example = "최우수상에 도전할 팀원을 모집합니다.")
    protected String notice;

    @ApiModelProperty(name="팀 소개", example = "팀의 목표, 개발 방향성, 어떤 팀원을 원하는지 등을 자유롭게 작성해 주세요")
    protected String introduction;

    @ApiModelProperty(name="기술 스택", example = "[]")
    List<TeamStackDto> techStacks = new ArrayList<>();

    @ApiModelProperty(name="전체 모집 인원", example = "6")
    protected Integer totalRecruitment;

    @ApiModelProperty(name="프론트엔드 모집 인원", example = "3")
    protected Integer frontendRecruitment;

    @ApiModelProperty(name="백엔드 모집 인원", example = "3")
    protected Integer backendRecruitment;

    @ApiModelProperty(name="현재 전체 모집 인원", example = "3")
    Integer totalHeadcount;

    @ApiModelProperty(name="현재 프론트엔드 모집 인원", example = "2")
    Integer frontendHeadcount;

    @ApiModelProperty(name="현재 백엔드 모집 인원", example = "1")
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
