package com.ssafy.ssafymate.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ssafy.ssafymate.common.ErrorResponseBody;
import com.ssafy.ssafymate.dto.request.TeamListReuestDto;
import com.ssafy.ssafymate.dto.response.TeamListResponseDto;
import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.TeamStack;
import com.ssafy.ssafymate.entity.TechStack;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            TeamListReuestDto teamListReuestDto,
            @RequestParam(required = false, defaultValue = "1", value = "page") Integer page
    ) {
        List<Team> teams = new ArrayList<>();
        System.out.println(teamListReuestDto);
        int front = 0;
        int back = 0;
        int total = 0;
        if (teamListReuestDto.getJob1() != null) {
            if (teamListReuestDto.getJob1().contains("프론트엔드")) {
                front = 1;

            } else if (teamListReuestDto.getJob1().equals("백엔드")) {
                back = 1;
            }
        }
        if(teamListReuestDto.getExclusion() != null && teamListReuestDto.getExclusion()== true){
            total = 1;
        }
        if (teamListReuestDto.getTeam_name() == null) {
            teamListReuestDto.setTeam_name("");
        }
        int totalPage = 0;
        int size = 8;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "t.id");
        if (teamListReuestDto.getSort() != null) {
            if (teamListReuestDto.getSort().equals("최신순")) {
                pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "t.create_date_time");
            } else if (teamListReuestDto.getSort().equals("인원순")) {
                pageable = PageRequest.of(page - 1, size, Sort.Direction.ASC, "t.total_headcount");
            }
        }
        Page<Team> teamp;


        try {

            System.out.println("stack");

            teamp = teamService.teamSearch(pageable,
                    teamListReuestDto,
                    front, back, total);

            teams = teamp.getContent();

            totalPage = teamp.getTotalPages();

        } catch (Exception exception) {
            System.out.println(exception);
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server, 팀 리스트 조회 실패"));
        }


        return ResponseEntity.status(200).body(TeamListResponseDto.of(teams, totalPage, page, teamp.getTotalElements()));

    }
}
