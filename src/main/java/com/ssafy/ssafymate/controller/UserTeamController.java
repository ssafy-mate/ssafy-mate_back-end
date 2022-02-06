package com.ssafy.ssafymate.controller;

import com.ssafy.ssafymate.common.ErrorResponseBody;
import com.ssafy.ssafymate.service.TeamService;
import com.ssafy.ssafymate.service.UserTeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "팁 가입 API", tags = {"UserTeam"})
@RestController
@RequestMapping("/api/user-team")
public class UserTeamController {

    @Autowired
    UserTeamService userTeamService;

    @Autowired
    TeamService teamService;

    @PostMapping("/")
    @ApiOperation(value = "교육생 인증", notes = "SSAFY 캠퍼스, 학번, 이름으로 교육생 인증")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 409, message = "가입 오류"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> studentVerify(@RequestBody Boolean check){


        return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "이미 가입된 교육생 입니다."));
    }




}
