package com.ssafy.ssafymate.controller;

import com.ssafy.ssafymate.JWT.TokenProvider;
import com.ssafy.ssafymate.common.BaseResponseBody;
import com.ssafy.ssafymate.dto.request.LoginRequestDto;
import com.ssafy.ssafymate.dto.response.LoginResponseDto;
import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Api(value = "로그인 API", tags = {"signin"})
@RestController
@RequestMapping("/api/user")
public class LoginContoller {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    // 로그인 시 토큰 생성
    @PostMapping("/signin")
    @ApiOperation(value = "로그인", notes = "이메일과 비밀번호를 받아서 확인한 뒤 토큰 생성")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> userCheckAndSendToken(
            @RequestBody LoginRequestDto loginRequestDto) {

        User user = userService.getUserByEmail(loginRequestDto.getEmail());
        if(user == null){
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, false, "아이디 또는 비밀번호를 확인하세요."));
        }
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, false, "아이디 또는 비밀번호를 확인하세요."));
        }
        String token = tokenProvider.createToken(loginRequestDto.getEmail());
        return ResponseEntity.status(200).body(LoginResponseDto.of(200, true, "success", token));
    }
}
