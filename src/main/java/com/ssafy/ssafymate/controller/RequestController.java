package com.ssafy.ssafymate.controller;

import com.ssafy.ssafymate.common.ErrorResponseBody;
import com.ssafy.ssafymate.dto.request.MessageUserRequestDto;
import com.ssafy.ssafymate.dto.response.LoginResponseDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/user/request")
    @ApiOperation(value = "팀 지원 요청", notes = "팀 지원 요청 (사용자 -> 팀)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = LoginResponseDto.class),
            @ApiResponse(code = 401, message = "인증 실패", response = ErrorResponseBody.class ,responseContainer = "List"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> userCheckAndSendToken(
            @RequestBody MessageUserRequestDto messageUserRequestDto,
            @AuthenticationPrincipal final String token) {

        try {

            if(token == null){
                return ResponseEntity.status(401).body(ErrorResponseBody.of(401, false, "토큰이 유효하지 않습니다."));
            }
            User user = userService.getUserByEmail(token);
            Long userId = user.getId();
            Team team = teamService.teamfind(messageUserRequestDto.getTeamId()).orElse(null);
            if(team==null){
                return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "해당 팀은 존재하지 않습니다."));
            }
            if (teamService.belongToTeam(team.getProject(),userId).orElse(null) != null) {
                return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "사용자는 이미 팀에 속해있어 요청이 불가능합니다."));
            }
            if(userTeamService.isRecruit(messageUserRequestDto.getTeamId())==false){
                return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "해당 팀은 팀원 모집이 마감되었습니다."));
            }
            requestMessageService.userRequest(user,team,messageUserRequestDto.getMessage());


        }catch (Exception exception){
            System.out.println(exception);
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server Error, 팀 지원 요청 실패"));
        }
        return ResponseEntity.status(200).body(ErrorResponseBody.of(200, true, "성공"));
    }
}
