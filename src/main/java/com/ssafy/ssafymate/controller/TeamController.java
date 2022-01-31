package com.ssafy.ssafymate.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ssafy.ssafymate.JWT.TokenProvider;
import com.ssafy.ssafymate.common.ErrorResponseBody;
import com.ssafy.ssafymate.common.MessageBody;
import com.ssafy.ssafymate.dto.request.TeamListReuestDto;
import com.ssafy.ssafymate.dto.request.TeamRequestDto;
import com.ssafy.ssafymate.dto.response.TeamListResponseDto;
import com.ssafy.ssafymate.dto.response.TeamResponseDto;
import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.TeamStack;
import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.entity.UserStack;
import com.ssafy.ssafymate.service.TeamService;
import com.ssafy.ssafymate.service.UserService;
import com.ssafy.ssafymate.service.UserTeamService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "팀 API", tags = {"Team"})
@RestController
@RequestMapping("/api/auth/team")
public class TeamController {

    @Autowired
    TeamService teamService;

    @Autowired
    UserService userService;

    @Autowired
    UserTeamService userTeamService;

    @Autowired
    private TokenProvider tokenProvider;

    @GetMapping("/{teamId}")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "팀 상세조회", notes = "팀 아이디로 해당 팀 상세 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "인증 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> findTeam(
            @PathVariable final Long teamId) {
        Team teamdata;
        try {
        teamdata = teamService.teamfind(teamId).orElse(null);
        if (teamdata==null) {
            return ResponseEntity.status(405).body(ErrorResponseBody.of(405, false,  "해당 팀 정보가 존재하지 않습니다."));
        }
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false,  "Internal Server Error, 팀 상세 정보 조회 실패"));
        }
        return ResponseEntity.status(200).body(TeamResponseDto.of(teamdata));
    }

    @PostMapping("/")
    @ApiOperation(value = "팀 생성", notes = "작성된 팀 정보와 유저 아이디를 가지고 팀생성")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> createTeam(
            @Valid TeamRequestDto teamRequestDto,
            @AuthenticationPrincipal String token) {
        try {
            User user = userService.getUserByEmail(token);
            Team team = teamService.teamSave(teamRequestDto, teamRequestDto.getTeamImg(),user);
            userTeamService.userTamSave(user,team);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false,  "Internal Server, 팀 생성 실패"));
        }
        return ResponseEntity.status(200).body(MessageBody.of("팀을 성공적으로 생성하였습니다."));
    }

    @PutMapping("/{teamId}")
    @ApiOperation(value = "팀 수정", notes = "수정된 팀 정보를 가지고 팀 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "인증 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> modifyTeam(
            @RequestPart(value= "teamRequestDto") TeamRequestDto teamRequestDto,
            @PathVariable final Long teamId,
            @AuthenticationPrincipal String token) {
        try {
            User user = userService.getUserByEmail(token);
            Long userId = user.getId();
            Team team = teamService.ownTeam(teamId,userId).orElse(null);
            if (team==null || !team.getOwner().getEmail().equals(token)) {
                return ResponseEntity.status(400).body(ErrorResponseBody.of(400, false,  "팀 수정 권한이 없습니다."));
            }
            teamService.teamModify(teamRequestDto, teamRequestDto.getTeamImg(), user, teamId);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false,  "Internal Server, 팀 상세 정보 수정 실패"));
        }
        return ResponseEntity.status(200).body(MessageBody.of("팀 상세 정보 수정이 완료되었습니다."));
    }

    @DeleteMapping("/{teamId}")
    @ApiOperation(value = "팀 삭제", notes = "팀장이 팀 삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "인증 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> deleteTeam(
            @PathVariable final Long teamId,
            @AuthenticationPrincipal String token) {
        try {
            User user = userService.getUserByEmail(token);
            Long userId = user.getId();
            Team team = teamService.ownTeam(teamId, userId).orElse(null);
            if (team==null || !team.getOwner().getEmail().equals(token)) {
                return ResponseEntity.status(400).body(ErrorResponseBody.of(400, false,  "팀 삭제에 권한이 없습니다."));
            }
            teamService.teamDelete(teamId);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false,  "Internal Server, 팀 삭제 실패"));
        }
        return ResponseEntity.status(200).body(MessageBody.of("팀 삭제가 완료되었습니다."));
    }

    @GetMapping("/teamList")
    @ApiOperation(value = "팀 리스트 조회", notes = "프로젝트, 프로젝트 트랙, 기술스택을 가지고 팀 리스트 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "인증 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> SearchTeam(
            TeamListReuestDto teamListReuestDto,
            @RequestParam(required = false, defaultValue = "1", value = "nowPage") Integer nowPage) {
        List<Team> teams = new ArrayList<>();
        System.out.println(teamListReuestDto);
        int front = 0;
        int back = 0;
        if (teamListReuestDto.getJob() != null) {
            if (teamListReuestDto.getJob().contains("프론트엔드")) {
                front = 1;
            } else if (teamListReuestDto.getJob().equals("백엔드")) {
                back = 1;
            }
        }
        if (teamListReuestDto.getTeamName() == null) {
            teamListReuestDto.setTeamName("");
        }
        int totalPage = 0;
        int size = 8;
        Pageable pageable = PageRequest.of(nowPage - 1, size, Sort.Direction.DESC, "t.id");
        if (teamListReuestDto.getSort()!=null) {
            if (teamListReuestDto.getSort().equals("최신순")) {
                pageable = PageRequest.of(nowPage - 1, size, Sort.Direction.DESC, "t.create_date_time");
            } else if (teamListReuestDto.getSort().equals("인원순")) {
                pageable = PageRequest.of(nowPage - 1, size, Sort.Direction.ASC, "t.total_headcount");
            }
        }
        Page<Team> teamp;

        List<TeamStack> techStacks = new ArrayList<>();
        if (teamListReuestDto.getTechStacks() != null) {
            // teamListReuestDto 에서 String 형태의 techStacks 찾기
            String jsonString = teamListReuestDto.getTechStacks();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type listType = new TypeToken<ArrayList<TeamStack>>(){}.getType();
            techStacks = gson.fromJson(jsonString, listType);
        }
        try {
            if ((techStacks.size() == 0) || (teamListReuestDto.getTechStacks()==null)) {
                System.out.println("not stack");
//                teams = teamService.teamSearch2(teamListReuestDto.getProject(),
//                        teamListReuestDto.getProjectTrack(),
//                        teamListReuestDto.getTeamName(),
//                        front, back).orElse(null);
//                System.out.println(teams);
                teamp = teamService.teamSearch(pageable,
                        teamListReuestDto.getCampus(),
                        teamListReuestDto.getProject(),
                        teamListReuestDto.getProjectTrack(),
                        teamListReuestDto.getTeamName(),
                        front, back);
                teams = teamp.getContent();
                System.out.println(teamp.getTotalElements());
                System.out.println(teamp.getTotalPages());
                totalPage = teamp.getTotalPages();
            }
            else {
                System.out.println("stack");

                List<Long> teamstacks = techStacks.stream().map(e -> e.getTechStackCode()).collect(Collectors.toList());
//                List<Long> teamstacks = teamListReuestDto.getTechStacks();
//                teams = teamService.teamSearch(teamListReuestDto.getProject(),
//                        teamListReuestDto.getProjectTrack(),
//                        teamListReuestDto.getTeamName() ,
//                        front, back,
//                        teamstacks ).orElse(null);
                teamp = teamService.teamSearch(pageable,
                        teamListReuestDto.getCampus(),
                        teamListReuestDto.getProject(),
                        teamListReuestDto.getProjectTrack(),
                        teamListReuestDto.getTeamName(),
                        front, back,
                        teamstacks);
                teams = teamp.getContent();

                totalPage = teamp.getTotalPages();
            }
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false,  "Internal Server, 팀 리스트 조회 실패"));
        }
        return ResponseEntity.status(200).body(TeamListResponseDto.of(teams,totalPage,nowPage,teamp.getTotalElements()));
    }
}
