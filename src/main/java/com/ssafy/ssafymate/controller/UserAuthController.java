package com.ssafy.ssafymate.controller;

import com.ssafy.ssafymate.common.BaseResponseBody;
import com.ssafy.ssafymate.dto.response.UserResponseDto;
import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "교육생 auth API", tags = {"UserAuth"})
@RestController
@RequestMapping("/api/auth/user")
public class UserAuthController {
    @Autowired
    UserService userService;


    //유저 상세 정보 조회
    @GetMapping("/{userId}")
    public ResponseEntity<? extends BaseResponseBody> userDetail(
            @PathVariable final Long userId
    ) {

        User user;
        try {
            user = userService.getUserById(userId);

            if (user == null) {
                return ResponseEntity.status(400).body(BaseResponseBody.of(400, false, "해당 교육생 정보가 존재하지 않습니다."));
            }

        } catch (Exception exception) {
            return ResponseEntity.status(500).body(BaseResponseBody.of(500, false, "Internal Server Error, 교육생 상세 정보 조회 실패"));
        }

        return ResponseEntity.status(200).body(UserResponseDto.of(200, true, "교육생 상세 정보 조회 성공", user));
    }
}
