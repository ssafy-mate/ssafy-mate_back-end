package com.ssafy.ssafymate.controller;

import com.ssafy.ssafymate.common.ErrorResponseBody;
import com.ssafy.ssafymate.dto.response.BelongToTeam;
import com.ssafy.ssafymate.dto.response.UserResponseDto;
import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.service.TeamService;
import com.ssafy.ssafymate.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(value = "교육생 auth API", tags = {"UserAuth"})
@RestController
@RequestMapping("/api/auth/user")
public class UserAuthController {
    @Autowired
    UserService userService;

    @Autowired
    TeamService teamService;

    @GetMapping("/team")
    @ApiOperation(value = "팀 참여 여부 조회", notes = "유저 아이디와 선택한 프로젝트로 해당 프로젝트에서 이미 팀에 참여 했는지 여부를 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> canCreateTeam(
            @RequestParam final String selectedProject,
            @AuthenticationPrincipal final String token){

        Boolean belongToTeam = false;

        try {
            User user = userService.getUserByEmail(token);
            Long userId = user.getId();
            Team team = teamService.belongToTeam(selectedProject,userId).orElse(null);
            if(team == null){
                belongToTeam = true;
            }
        }catch (Exception exception){
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false,  "Internal Server, 팀 참여 여부 조회 실패"));
        }

        return ResponseEntity.status(200).body(BelongToTeam.of(belongToTeam));
    }


    //교육생 상세 정보 조회
    @GetMapping("/{userId}")
    public ResponseEntity<?> userDetail(
            @PathVariable final Long userId
    ) {

        User user;
        try {
            user = userService.getUserById(userId);

            if (user == null) {
                return ResponseEntity.status(405).body(ErrorResponseBody.of(405, false, "해당 교육생 정보가 존재하지 않습니다."));
            }

        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server Error, 교육생 상세 정보 조회 실패"));
        }

        return ResponseEntity.status(200).body(UserResponseDto.of(user));
    }
}