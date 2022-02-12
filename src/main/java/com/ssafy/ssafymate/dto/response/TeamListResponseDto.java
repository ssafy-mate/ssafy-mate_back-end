package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.dto.TeamDto.TeamBoardDto;
import com.ssafy.ssafymate.entity.Team;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("TeamList")
public class TeamListResponseDto {

    @ApiModelProperty(name="팀 리스트", example = "[]")
    List<TeamBoardDto> teams;

    @ApiModelProperty(name="전체 페이지", example = "3")
    Integer totalPage;

    @ApiModelProperty(name="현재 페이지", example = "1")
    Integer nowPage;

    @ApiModelProperty(name="엘리멘탈 개수", example = "20")
    Long totalElement;

    public static TeamListResponseDto of(List<Team> teams, Integer totalPage, Integer nowPage, Long totalElement){

        TeamListResponseDto res = new TeamListResponseDto();
        res.setTeams(TeamBoardDto.of(teams));
        res.setTotalPage(totalPage);
        res.setNowPage(nowPage);
        res.setTotalElement(totalElement);
        return res;

    }

}
