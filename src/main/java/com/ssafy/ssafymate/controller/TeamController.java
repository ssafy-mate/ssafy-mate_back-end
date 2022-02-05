package com.ssafy.ssafymate.controller;

import com.ssafy.ssafymate.JWT.TokenProvider;
import com.ssafy.ssafymate.common.ErrorResponseBody;
import com.ssafy.ssafymate.common.MessageBody;
import com.ssafy.ssafymate.dto.request.TeamRequestDto;
import com.ssafy.ssafymate.dto.response.TeamResponseDto;
import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.service.TeamService;
import com.ssafy.ssafymate.service.UserService;
import com.ssafy.ssafymate.service.UserTeamService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @GetMapping("/info/{teamId}")
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


    @PostMapping("/info")
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

    @PutMapping("/info/{teamId}")
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

    @DeleteMapping("/info/{teamId}")
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


}
