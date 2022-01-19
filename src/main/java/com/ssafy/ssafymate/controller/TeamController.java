package com.ssafy.ssafymate.controller;

import com.ssafy.ssafymate.common.BaseResponseBody;
import com.ssafy.ssafymate.dto.request.TeamRequestDto;
import com.ssafy.ssafymate.service.TeamService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "팀 API", tags = {"Team"})
@RestController
@RequestMapping("/api/auth/team")
public class TeamController {

    @Autowired
    TeamService teamService;

    @PostMapping("/")
    public ResponseEntity<? extends BaseResponseBody> createTeam(
                @RequestPart(value= "teamRequestDto")TeamRequestDto teamRequestDto,
            @RequestPart(value= "file", required = false) MultipartFile multipartFile) throws Exception {

        System.out.println(teamRequestDto.toString());
        System.out.println(multipartFile.getOriginalFilename());

        teamService.teamSave(teamRequestDto, multipartFile);
//        try {
//            teamService.teamSave(teamRequestDto, multipartFile);
//        } catch (Exception exception) {
//            return ResponseEntity.status(400).body(BaseResponseBody.of(400, false,  "필수 입력 사항이 모두 입력되지 않았습니다."));
//        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, true,  "success"));
    }
}
