package com.ssafy.ssafymate.controller;

import com.ssafy.ssafymate.common.ErrorResponseBody;
import com.ssafy.ssafymate.dto.TeamDto.TeamInt;
import com.ssafy.ssafymate.dto.request.TeamListRequestDto;
import com.ssafy.ssafymate.dto.response.TeamListResponseDto;
import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.service.TeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(value = "팀 리스트 API", tags = {"TeamList"})
@RestController
@RequestMapping("/api/auth/project")
public class TeamListController {

    @Autowired
    TeamService teamService;

    @GetMapping("/team-list")
    @ApiOperation(value = "팀 리스트 조회", notes = "프로젝트, 프로젝트 트랙, 기술스택을 가지고 팀 리스트 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "인증 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> SearchTeam(
            TeamListRequestDto teamListRequestDto,
            @RequestParam(required = false, defaultValue = "1", value = "page") Integer page
    ) {
        List<Team> teams = new ArrayList<>();
        System.out.println(teamListRequestDto);
        int front = 0;
        int back = 0;
        int total = 0;
        if (teamListRequestDto.getJob1() != null) {
            if (teamListRequestDto.getJob1().contains("프론트엔드")) {
                front = 1;

            } else if (teamListRequestDto.getJob1().equals("백엔드")) {
                back = 1;
            }
        }
        if(teamListRequestDto.getExclusion() != null && teamListRequestDto.getExclusion()== true){
            total = 1;
        }
        if (teamListRequestDto.getTeam_name() == null) {
            teamListRequestDto.setTeam_name("");
        }
        int totalPage = 0;
        int size = 8;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "t.id");
        if (teamListRequestDto.getSort() != null) {
            if (teamListRequestDto.getSort().equals("최신순")) {
                pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "t.create_date_time");
            } else if (teamListRequestDto.getSort().equals("인원순")) {
                pageable = PageRequest.of(page - 1, size, Sort.Direction.ASC, "t.total_headcount");
            }
        }
        Page<Team> teamp;

        Page<TeamInt> teamInts;
        try {

            System.out.println("stack");

            teamp = teamService.teamSearch(pageable,
                    teamListRequestDto,
                    front, back, total);

            teamInts = teamService.teamSearch2(pageable,
                    teamListRequestDto,
                    front, back, total);
            System.out.println(teamInts.getContent().get(0));
            System.out.println(teamInts.getContent().get(0).getBackend_headcount());
//            teams = teamp.getContent();

            teams = teamService.teamListTransfer(teamInts.getContent());
            totalPage = teamp.getTotalPages();

        } catch (Exception exception) {
            System.out.println(exception);
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server, 팀 리스트 조회 실패"));
        }


        return ResponseEntity.status(200).body(TeamListResponseDto.of(teams, totalPage, page, teamp.getTotalElements()));

    }
}
