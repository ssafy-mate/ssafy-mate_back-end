package com.ssafy.ssafymate.controller;


import com.ssafy.ssafymate.common.ErrorResponseBody;
import com.ssafy.ssafymate.dto.response.TechStacksResponseDto;
import com.ssafy.ssafymate.dto.response.UserResponseDto;
import com.ssafy.ssafymate.entity.TechStack;
import com.ssafy.ssafymate.service.TechStackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(value = "기술 스택 API", tags = {"tech_stack"})
@RestController
@RequestMapping("/api/techstack-list")
public class TechStackController {


    @Autowired
    TechStackService techStackService;

    @GetMapping()
    @ApiOperation(value = "교육생 인증", notes = "SSAFY 캠퍼스, 학번, 이름으로 교육생 인증")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })public ResponseEntity<?> userDetail(){
        List<TechStack> techStacks = new ArrayList<>();

        try {
            techStacks = techStackService.techstackList();
        }catch (Exception e){
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server Error, 기술 스택 리스트 조회 실패"));
        }

        return ResponseEntity.status(200).body(TechStacksResponseDto.of(techStacks));
    }
}
