package com.ssafy.ssafymate.controller;

import com.ssafy.ssafymate.dto.response.LoginResponseDto;
import com.ssafy.ssafymate.dto.response.ProTeamResponseDto;
import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.service.ProService;
import com.ssafy.ssafymate.service.TeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "프로 API", tags = {"pro"})
@RestController
@RequestMapping("/api/pro")
public class ProController {

    @Autowired
    private ProService proService;

    @GetMapping("/findTeam")
    @ApiOperation(value = "로그인", notes = "이메일과 비밀번호를 받아서 확인한 뒤 토큰 생성")
    public ResponseEntity<?> findTeam(
            @RequestParam String campus,
            @RequestParam String project,
            @RequestParam String projectTrack
    ){
        List<Team> teams = proService.findTeam(campus, project, projectTrack);

        return ResponseEntity.status(200).body(ProTeamResponseDto.of(teams));
    }
}
