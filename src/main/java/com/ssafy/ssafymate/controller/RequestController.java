package com.ssafy.ssafymate.controller;

import com.ssafy.ssafymate.common.ErrorResponseBody;
import com.ssafy.ssafymate.common.MessageBody;
import com.ssafy.ssafymate.common.SuccessMessageBody;
import com.ssafy.ssafymate.dto.request.MessageResponseRequestDto;
import com.ssafy.ssafymate.dto.request.MessageTeamRequestDto;
import com.ssafy.ssafymate.dto.request.MessageUserRequestDto;
import com.ssafy.ssafymate.dto.response.LoginResponseDto;
import com.ssafy.ssafymate.dto.response.RequestMessageListResponseDto;
import com.ssafy.ssafymate.entity.RequestMessage;
import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.service.RequestMessageService;
import com.ssafy.ssafymate.service.TeamService;
import com.ssafy.ssafymate.service.UserService;
import com.ssafy.ssafymate.service.UserTeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Api(value = "지원 요청 API", tags = {"requset"})
@RestController
@RequestMapping("/api/auth")
public class RequestController {

    @Autowired
    TeamService teamService;

    @Autowired
    UserTeamService userTeamService;

    @Autowired
    UserService userService;

    @Autowired
    RequestMessageService requestMessageService;

    @PostMapping("/requests/users")
    @ApiOperation(value = "팀 지원 요청", notes = "팀 지원 요청 (사용자 -> 팀)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = LoginResponseDto.class),
            @ApiResponse(code = 401, message = "인증 실패", response = ErrorResponseBody.class, responseContainer = "List"),
            @ApiResponse(code = 409, message = "요청 불가"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> userRequest(
            @RequestBody MessageUserRequestDto messageUserRequestDto,
            @AuthenticationPrincipal final String token) {

        try {

            if (token == null) {
                return ResponseEntity.status(401).body(ErrorResponseBody.of(401, false, "토큰이 유효하지 않습니다."));
            }
            User sender = userService.getUserByEmail(token);
            Long senderId = sender.getId();
            Team team = teamService.teamFind(messageUserRequestDto.getTeamId());
            if (team == null) {
                return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "해당 팀은 존재하지 않습니다."));
            } else if (requestMessageService.findSameRequest(senderId, messageUserRequestDto.getTeamId(), team.getOwner().getId()) != null) {
                return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "이미 제안한 요청입니다."));
            } else if ((team.getProject().equals("공통 프로젝트") && !team.getProjectTrack().equals(sender.getCommonProjectTrack())) ||
                    (team.getProject().equals("특화 프로젝트") && !team.getProjectTrack().equals(sender.getSpecializationProjectTrack()))) {
                return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "사용자 프로젝트 트랙과 해당 팀 트랙이 일치하지 않습니다."));
            } else if (teamService.belongToTeam(team.getProject(), senderId) != null) {
                return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "사용자는 이미 팀에 속해있어 요청이 불가능합니다."));
            } else if (userTeamService.isRecruit(messageUserRequestDto.getTeamId()).equals("false")) {
                return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "해당 팀은 팀원 모집이 마감되었습니다."));
            }
            requestMessageService.userRequest(sender, team, messageUserRequestDto.getMessage());


        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server Error, 팀 지원 요청 실패"));
        }
        return ResponseEntity.status(200).body(SuccessMessageBody.of(true, "팀 지원이 완료되었습니다."));
    }

    @PostMapping("/requests/teams")
    @ApiOperation(value = "팀 합류 요청", notes = "팀 합류 요청 (팀 (팀장) -> 사용자)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = LoginResponseDto.class),
            @ApiResponse(code = 401, message = "인증 실패", response = ErrorResponseBody.class, responseContainer = "List"),
            @ApiResponse(code = 409, message = "요청 불가"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> teamRequest(
            @RequestBody MessageTeamRequestDto messageTeamRequestDto,
            @AuthenticationPrincipal final String token) {

        try {

            if (token == null) {
                return ResponseEntity.status(401).body(ErrorResponseBody.of(401, false, "토큰이 유효하지 않습니다."));
            }
            User sender = userService.getUserByEmail(token);
            Long senderId = sender.getId();

            Long receiverId = messageTeamRequestDto.getUserId();
            User receiver = userService.getUserById(receiverId);

            Team team = teamService.belongToTeam(messageTeamRequestDto.getProject(), senderId);
            if (receiver == null) {
                return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "존재하지 않는 교육생 입니다."));
            } else if (team == null) {
                return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "팀 생성 후 다시 요청을 시도해주세요."));
            } else if (teamService.ownTeam(team.getId(), senderId) == null) {
                return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "사용자는 팀 합류 요청 권한이 없습니다."));
            } else if (requestMessageService.findSameRequest(senderId, team.getId(), receiverId) != null) {
                return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "이미 제안한 요청입니다."));
            } else if ((team.getProject().equals("공통 프로젝트") && !team.getProjectTrack().equals(receiver.getCommonProjectTrack())) ||
                    (team.getProject().equals("특화 프로젝트") && !team.getProjectTrack().equals(receiver.getSpecializationProjectTrack()))) {
                return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "해당 교육생의 프로젝트 트랙이 팀의 프로젝트 트랙과 일치하지 않습니다."));
            } else if (teamService.belongToTeam(team.getProject(), receiverId) != null) {
                return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "해당 교육생은 이미 다른 팀에 합류되어 있습니다"));
            } else if (userTeamService.isRecruit(team.getId()).equals("false")) {
                return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "해당 팀은 팀원 모집이 마감되었습니다."));
            }
            requestMessageService.teamRequest(sender, messageTeamRequestDto.getUserId(), team, messageTeamRequestDto.getMessage());


        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server Error, 팀 지원 요청 실패"));
        }
        return ResponseEntity.status(200).body(SuccessMessageBody.of(true, "팀 합류 요청이 완료되었습니다."));
    }

    @GetMapping("/users/{userId}/receive-requests")
    @ApiOperation(value = "받은 제안", notes = "사용자가 받은 팀/사용자 요청들")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = LoginResponseDto.class),
            @ApiResponse(code = 401, message = "인증 실패", response = ErrorResponseBody.class, responseContainer = "List"),
            @ApiResponse(code = 409, message = "요청 불가"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> receiveRequest(
            @RequestParam String project,
            @AuthenticationPrincipal final String token, @PathVariable String userId) {
        System.out.println(project);
        List<RequestMessage> messages;
        try {
            User user = userService.getUserByEmail(token);

            messages = requestMessageService.receiveList(user, project);

        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server Error, 받은 제안 요청 실패"));
        }
        return ResponseEntity.status(200).body(RequestMessageListResponseDto.of(messages, "receiver"));
    }

    @GetMapping("/users/{userId}/send-requests")
    @ApiOperation(value = "보낸 제안", notes = "사용자가 받은 팀/사용자 요청들")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = LoginResponseDto.class),
            @ApiResponse(code = 401, message = "인증 실패", response = ErrorResponseBody.class, responseContainer = "List"),
            @ApiResponse(code = 409, message = "요청 불가"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> sendRequest(
            @RequestParam String project,
            @AuthenticationPrincipal final String token, @PathVariable String userId) {
        System.out.println(project);
        List<RequestMessage> messages;
        try {
            User user = userService.getUserByEmail(token);

            messages = requestMessageService.sendList(user, project);


        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server Error, 제안 요청 실패"));
        }
        return ResponseEntity.status(200).body(RequestMessageListResponseDto.of(messages, "sender"));
    }

    @PutMapping("/requests/responses")
    @ApiOperation(value = "제안 수락/거절/취소", notes = "제안 수락/거절/취소")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = LoginResponseDto.class),
            @ApiResponse(code = 401, message = "인증 실패", response = ErrorResponseBody.class, responseContainer = "List"),
            @ApiResponse(code = 409, message = "요청 불가"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> requestResponse(
            @RequestBody MessageResponseRequestDto messageResponseRequestDto,
            @AuthenticationPrincipal final String token) {
        RequestMessage requestMessage;
        Long requestId = messageResponseRequestDto.getRequestId();
        String response = messageResponseRequestDto.getResponse();
        User user;
        Team team;
        Integer answer = 0;
        String message = "응답 완료";
        try {
            user = userService.getUserByEmail(token);

            requestMessage = requestMessageService.getRequest(requestId);

            team = teamService.teamFind(requestMessage.getTeamId());

            if (response.equals("cancellation")) {

                if (!Objects.equals(requestMessage.getSenderId(), user.getId())) {
                    return ResponseEntity.status(403).body(ErrorResponseBody.of(403, false, "응답 권한이 없습니다."));
                }
                answer = requestMessageService.updateReadCheckRejection(requestId, response);
                message = "요청 취소 완료";
            } else {
                if (!Objects.equals(requestMessage.getReceiverId(), user.getId())) {
                    return ResponseEntity.status(403).body(ErrorResponseBody.of(403, false, "응답 권한이 없습니다."));
                } else if (response.equals("rejection")) {
                    answer = requestMessageService.updateReadCheckRejection(requestId, response);
                } else if (response.equals("approval")) {
                    Long userId = 0L;
                    if (userTeamService.isRecruit(team.getId()).equals("false")) {
                        requestMessageService.updateReadCheckRejection(requestId, "cancellation");
                        return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "해당 팀은 팀원 모집이 마감되었습니다."));
                    } else if (requestMessage.getType().equals("teamRequest")) {
                        userId = requestMessage.getReceiverId();
                        if (teamService.belongToTeam(team.getProject(), userId) != null) {
                            requestMessageService.updateReadCheckRejection(requestId, "cancellation");
                            return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "사용자는 이미 팀에 속해있어 응답이 불가능합니다."));
                        }
                    } else if (requestMessage.getType().equals("userRequest")) {
                        userId = requestMessage.getSenderId();

                        if (teamService.belongToTeam(team.getProject(), userId) != null) {
                            requestMessageService.updateReadCheckRejection(requestId, "cancellation");
                            return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "해당 교육생은 이미 다른 팀에 합류되어 있습니다."));
                        }
                    }
                    answer = requestMessageService.updateReadCheckApproval(requestId, response, userId, team);
                }

            }
            if (answer <= 0) {
                return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "이미 처리된 제안 입니다."));
            }


        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server Error, 제안 요청 응답 실패"));
        }
        return ResponseEntity.status(200).body(MessageBody.of(message));
    }

}
