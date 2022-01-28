package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.common.ErrorResponseBody;
import com.ssafy.ssafymate.dto.TeamDto.TeamBoardDto;
import com.ssafy.ssafymate.entity.Team;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("TeamList")
public class TeamListResponseDto {
    @ApiModelProperty(name="팀 리스트", example = "teams :[]")
    List<TeamBoardDto> teams;

    Integer totalPage;

    Integer nowPage;

    public static TeamListResponseDto of(List<Team> teams, Integer totalPage, Integer nowPage){
        TeamListResponseDto res = new TeamListResponseDto();
        res.setTeams(TeamBoardDto.of(teams));
        res.setTotalPage(totalPage);
        res.setNowPage(nowPage);
        return res;
    }
}
