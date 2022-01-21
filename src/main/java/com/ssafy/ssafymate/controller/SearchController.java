package com.ssafy.ssafymate.controller;

import com.ssafy.ssafymate.common.BaseResponseBody;
import com.ssafy.ssafymate.dto.response.EmailResponseDto;
import com.ssafy.ssafymate.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "아이디, 비밀번호 찾기 API", tags = {"search"})
@RestController
@RequestMapping("/api/user")
public class SearchController {

    @Autowired
    UserService userService;

    // 아이디 찾기
    @GetMapping("/id-search")
    @ApiOperation(value = "아이디 찾기", notes = "SSAFY 학번, 이름으로 아이디(이메일) 찾기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> idSearch(
            @RequestParam String studentNumber, @RequestParam String studentName) {
        String email;
        try {
            email = userService.getEmailByStudentNumberAndStudentName(studentNumber, studentName);
        } catch (NullPointerException exception) {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, false, "일치하는 회원 정보가 없습니다."));
        }
        return ResponseEntity.status(200).body(EmailResponseDto.of(200, true, "success", email));
    }

    // 비밀번호 찾기

}
