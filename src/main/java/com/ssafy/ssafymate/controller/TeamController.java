package com.ssafy.ssafymate.controller;

import com.ssafy.ssafymate.common.ErrorResponseBody;
import com.ssafy.ssafymate.common.MessageBody;
import com.ssafy.ssafymate.dto.request.TeamRequestDto;
import com.ssafy.ssafymate.dto.response.TeamDetailResponseDto;
import com.ssafy.ssafymate.dto.response.TeamModifyResponseDto;
import com.ssafy.ssafymate.dto.response.TeamResponseDto;
import com.ssafy.ssafymate.entity.ProjectDeadline;
import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.service.ProjectDeadlineService;
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
import java.time.LocalDateTime;
import java.util.Objects;

@Api(value = "팀 API", tags = {"Team"})
@RestController
@RequestMapping("/api/auth/teams")
public class TeamController {

    @Autowired
    TeamService teamService;

    @Autowired
    UserService userService;

    @Autowired
    UserTeamService userTeamService;

    @Autowired
    ProjectDeadlineService projectDeadlineService;

    @GetMapping("/{teamId}")
    @ApiOperation(value = "팀 상세조회", notes = "팀 아이디로 해당 팀 상세 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> findTeam(
            @PathVariable final Long teamId,
            @AuthenticationPrincipal String token) {

        User user;
        Team team;
        try {
            user = userService.getUserByEmail(token);
            team = teamService.teamFind(teamId);

            if (team == null) {
                return ResponseEntity.status(404).body(ErrorResponseBody.of(404, false, "해당 팀 정보가 존재하지 않습니다."));
            }
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server Error, 팀 상세 정보 조회 실패"));
        }
        return ResponseEntity.status(200).body(TeamDetailResponseDto.of(team, user));

    }


    @PostMapping("")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "팀 생성", notes = "작성된 팀 정보와 유저 아이디를 가지고 팀생성")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 409, message = "요청 불가"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> createTeam(
            @Valid TeamRequestDto teamRequestDto,
            @AuthenticationPrincipal String token) {

        User user;
        Team team;
        try {
            String project = teamRequestDto.getProject();

            ResponseEntity<?> check =this.checkDeadline(project);
            if(check!=null){
                return check;
            }

            user = userService.getUserByEmail(token);
            team = teamService.belongToTeam(project, user.getId());
            if (team != null) {
                return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "사용자는 이미 팀에 속해있어 팀 생성이 불가능합니다."));
            }
            if ((project.equals("특화 프로젝트") && !Objects.equals(teamRequestDto.getProjectTrack(), user.getSpecializationProjectTrack())) ||
                    (project.equals("공통 프로젝트") && !Objects.equals(teamRequestDto.getProjectTrack(), user.getCommonProjectTrack()))) {
                return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "사용자의 트랙과 일치하지 않아 팀 생성이 불가능합니다."));
            }

            team = teamService.teamSave(teamRequestDto, teamRequestDto.getTeamImg(), user);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server, 팀 생성 실패"));
        }
        return ResponseEntity.status(200).body(TeamResponseDto.of(team.getId(), "팀을 성공적으로 생성하였습니다."));

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{teamId}/edit")
    @ApiOperation(value = "팀 수정 정보 조회", notes = "팀 아이디로 해당 팀 수정 정보 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 403, message = "권한 없음"),
            @ApiResponse(code = 404, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> findModifyTeam(
            @PathVariable final Long teamId,
            @AuthenticationPrincipal String token) {

        User user;
        Team team;
        try {
            user = userService.getUserByEmail(token);
            team = teamService.teamFind(teamId);

            if(team!=null) {
                ResponseEntity<?> check = this.checkDeadline(team.getProject());
                if (check != null) {
                    return check;
                }
            }

            if (team == null) {
                return ResponseEntity.status(404).body(ErrorResponseBody.of(404, false, "해당 팀 정보가 존재하지 않습니다."));
            } else if (!Objects.equals(team.getOwner().getId(), user.getId())) {
                return ResponseEntity.status(403).body(ErrorResponseBody.of(403, false, "팀을 수정할 수 있는 권한이 없습니다."));
            }
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server Error, 팀 상세 정보 조회 실패"));
        }
        return ResponseEntity.status(200).body(TeamModifyResponseDto.of(team));

    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{teamId}")
    @ApiOperation(value = "팀 수정", notes = "수정된 팀 정보를 가지고 팀 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 403, message = "권한 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> modifyTeam(
            @Valid TeamRequestDto teamRequestDto,
            @PathVariable final Long teamId,
            @AuthenticationPrincipal String token) {

        User user;
        Team team;
        try {
            user = userService.getUserByEmail(token);
            Long userId = user.getId();
            team = teamService.ownTeam(teamId, userId);

            if(team!=null) {
                ResponseEntity<?> check = this.checkDeadline(team.getProject());
                if (check != null) {
                    return check;
                }
            }

            if (team == null || !team.getOwner().getEmail().equals(token)) {
                return ResponseEntity.status(403).body(ErrorResponseBody.of(403, false, "팀 수정 권한이 없습니다."));
            }
            teamService.teamModify(teamRequestDto, teamRequestDto.getTeamImg(), user, team);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server, 팀 상세 정보 수정 실패"));
        }
        return ResponseEntity.status(200).body(TeamResponseDto.of(team.getId(), "팀 상세 정보 수정이 완료되었습니다."));

    }

    @DeleteMapping("/{teamId}")
    @ApiOperation(value = "팀 삭제", notes = "팀장이 팀 삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 403, message = "권한 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> deleteTeam(
            @PathVariable final Long teamId,
            @AuthenticationPrincipal String token) {

        try {
            User user = userService.getUserByEmail(token);
            Long userId = user.getId();
            Team team = teamService.ownTeam(teamId, userId);

            if(team!=null) {
                ResponseEntity<?> check = this.checkDeadline(team.getProject());
                if (check != null) {
                    return check;
                }
            }

            if (team == null || !team.getOwner().getEmail().equals(token)) {
                return ResponseEntity.status(403).body(ErrorResponseBody.of(403, false, "팀을 삭제할 수 있는 권한이 없습니다."));
            }
            teamService.teamDelete(teamId);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server, 팀 삭제 실패"));
        }
        return ResponseEntity.status(200).body(MessageBody.of("새로운 팀에 지원해보세요."));

    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{teamId}/leave")
    @ApiOperation(value = "팀 탈퇴", notes = "팀원이 팀 탈퇴")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 403, message = "권한 없음"),
            @ApiResponse(code = 404, message = "잘못된 요청"),
            @ApiResponse(code = 409, message = "요청 불가"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> deleteUserTeam(
            @PathVariable final Long teamId,
            @AuthenticationPrincipal String token) {

        try {
            User user = userService.getUserByEmail(token);
            Long userId = user.getId();
            Team team = teamService.teamFind(teamId);

            if(team!=null) {
                ResponseEntity<?> check = this.checkDeadline(team.getProject());
                if (check != null) {
                    return check;
                }
            }

            if (team == null) {
                return ResponseEntity.status(404).body(ErrorResponseBody.of(404, false, "팀이 없습니다."));
            } else if (userTeamService.userTeamFind(userId, teamId) == null) {
                return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "합류된 팀이 아닙니다."));
            } else if (team.getOwner().getId().equals(userId)) {
                return ResponseEntity.status(403).body(ErrorResponseBody.of(403, false, "팀장은 팀을 탈퇴 할 수 없습니다."));
            }
            userTeamService.userTeamDelete(userId, teamId);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server, 팀 삭제 실패"));
        }
        return ResponseEntity.status(200).body(MessageBody.of("탈퇴 처리가 완료되었습니다."));

    }

    public ResponseEntity<?> checkDeadline(String project) {
        ProjectDeadline projectDeadline = projectDeadlineService.findProjectDeadline(project);
        if (projectDeadline == null || projectDeadline.getDeadline() == null) {
            return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "아직 팀빌딩 기간이 아닙니다."));
        } else if (projectDeadline.getDeadline().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "팀빌딩 기간이 끝났습니다."));
        }
        return null;
    }

}
