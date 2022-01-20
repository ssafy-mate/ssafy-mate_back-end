package com.ssafy.ssafymate.controller;

import com.ssafy.ssafymate.common.BaseResponseBody;
import com.ssafy.ssafymate.dto.request.TeamListReuestDto;
import com.ssafy.ssafymate.dto.request.TeamRequestDto;
import com.ssafy.ssafymate.dto.response.BelongToTeam;
import com.ssafy.ssafymate.dto.response.TeamListResponseDto;
import com.ssafy.ssafymate.dto.response.TeamResponseDto;
import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.TeamStack;
import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.service.TeamService;
import com.ssafy.ssafymate.service.UserService;
import com.ssafy.ssafymate.service.UserTeamService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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


    @GetMapping("/{teamId}")
    public ResponseEntity<? extends BaseResponseBody> findTeam(
            @PathVariable final Long teamId
    ){
        Team team = teamService.teamfind(teamId).orElse(null);

        return ResponseEntity.status(200).body(TeamResponseDto.of(200, true,  "success",team));
    }

    @GetMapping("/userIn")
    public ResponseEntity<?extends BaseResponseBody> canCreateTeam(
            @RequestParam final Long userId,
            @RequestParam final String selectedProject){



        Boolean belongToTeam = false;

        try {
                Team team = teamService.belongToTeam(selectedProject,userId).orElse(null);
                if(team == null){
                    belongToTeam = true;
                }
        }catch (Exception exception){
            return ResponseEntity.status(500).body(BaseResponseBody.of(400, false,  ""));
        }

        return ResponseEntity.status(200).body(BelongToTeam.of(200, true,  "success",belongToTeam));
    }

    @PostMapping("/")
    public ResponseEntity<? extends BaseResponseBody> createTeam(
                @RequestPart(value= "teamRequestDto")TeamRequestDto teamRequestDto,
            @RequestPart(value= "file", required = false) MultipartFile multipartFile,
                @RequestPart(value = "userId") String email) throws Exception {

        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(401).body(BaseResponseBody.of(401, false, "팀 생성 실패"));
        }
        try {
            Team team = teamService.teamSave(teamRequestDto, multipartFile,user);
            userTeamService.userTamSave(user,team);
        } catch (Exception exception) {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, false,  "필수 입력 사항이 모두 입력되지 않았습니다."));
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, true,  "success"));
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<? extends BaseResponseBody> modifyTeam(
            @RequestPart(value= "teamRequestDto")TeamRequestDto teamRequestDto,
            @RequestPart(value= "file", required = false) MultipartFile multipartFile,
            @RequestPart(value = "userId") String email,
            @PathVariable final Long teamId ) {

        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(401).body(BaseResponseBody.of(401, false, "팀 수정 권한 없음"));
        }
        try {
            teamService.teamModify(teamRequestDto, multipartFile, user, teamId);
        } catch (Exception exception) {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, false,  "필수 입력 사항이 모두 입력되지 않았습니다."));
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, true,  "success"));
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<? extends BaseResponseBody> deleteTeam(
            @PathVariable final Long teamId
    ){
        try {
            teamService.teamDelete(teamId);
        } catch (Exception exception) {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, false,  "팀 삭제에 실패하였습니다."));
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, true,  "success"));
    }

    @PostMapping("/teamList")
    public ResponseEntity<? extends BaseResponseBody> SearchTeam(
            @RequestBody TeamListReuestDto teamListReuestDto
            ){
        List<Team> teams;
        try {
            teams = teamService.teamSearch(teamListReuestDto.getProject(),teamListReuestDto.getProjectTrack(),teamListReuestDto.getTeamStacks()).orElse(null);
        }catch (Exception exception){
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, false,  "팀 조회에 실패하였습니다.."));
        }


        return ResponseEntity.status(200).body(TeamListResponseDto.of(200, true,  "success",teams));

    }
}
