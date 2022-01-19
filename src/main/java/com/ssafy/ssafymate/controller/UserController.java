package com.ssafy.ssafymate.controller;

import com.ssafy.ssafymate.common.BaseResponseBody;
import com.ssafy.ssafymate.dto.request.UserRequestDto;
import com.ssafy.ssafymate.entity.Student;
import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.exception.EmailCodeException;
import com.ssafy.ssafymate.service.EmailService;
import com.ssafy.ssafymate.service.StudentService;
import com.ssafy.ssafymate.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    StudentService studentService;

    @Autowired
    EmailService emailService;

    @Autowired
    UserService userService;

    // 회원가입 1단계 - 교육생 인증
    @GetMapping("/sign-up/verification/ssafy")
    @ApiOperation(value = "교육생 인증", notes = "SSAFY 캠퍼스, 학번, 이름으로 교육생 인증")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> studentVerify(
            @RequestParam String campus, @RequestParam String studentNumber, @RequestParam String studentName) {
        Student student = studentService.getStudentByStudentNumber(studentNumber);
        if(student==null || !((campus.equals(student.getCampus())) && (studentNumber.equals(student.getStudentNumber())) && (studentName.equals(student.getStudentName())))) {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, false, "일치하는 교육생 정보가 없습니다."));
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, true, "success"));
    }

    // 회원가입 2단계 - 이메일 인증
    @PostMapping("/sign-up/verification/email")
    @ApiOperation(value = "이메일 인증 요청 & 재요청", notes = "사용자가 입력한 이메일에 인증코드 전송")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> emailConfirm(
            @RequestBody @ApiParam(value="이메일 정보", required = true) String email) throws Exception {
        String confirm;

        User user = userService.getUserByEmail(email);
        if (user != null) {
            return ResponseEntity.status(401).body(BaseResponseBody.of(401, false, "이미 등록된 이메일입니다."));
        }

        try {
            confirm = emailService.sendSimpleMessage(email);
        } catch(Exception exception) {
            return ResponseEntity.status(500).body(BaseResponseBody.of(500, false, "이메일 전송에 실패하였습니다."));
        }

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, true, "success"));
    }

    // 회원가입 2단계 - 이메일 인증 확인
    @PutMapping("/sign-up/verification/email")
    @ApiOperation(value = "이메일 인증 코드 확인", notes = "이메일로 발송한 인증 코드를 확인")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> emailAuth(
            @RequestParam String email, @RequestParam String code) throws Exception {
        Long emailAuth;
        try {
            emailAuth = emailService.getUserIdByCode(email, code);
        } catch (EmailCodeException exception) {
            return ResponseEntity.status(401).body(BaseResponseBody.of(401, false,  "인증정보가 잘못되었습니다."));
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, true,  "success"));
    }

    // 회원가입 3단계
    @PostMapping(name="/")
    @ApiOperation(value = "계정 생성을 위한 프로필 작성", notes = "회원가입 1~3단계를 거쳐서 작성한 회원정보를 저장하고 회원가입 완료")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> signUp(
            @RequestPart(value= "userRequestDto") UserRequestDto userRequestDto,
            @RequestPart(value= "file", required = false) MultipartFile multipartFile) throws Exception {

        System.out.println(userRequestDto.toString());
        System.out.println(multipartFile.getOriginalFilename());

        userService.userSave(userRequestDto, multipartFile);
//        try {
//            userService.userSave(userRequestDto, multipartFile);
//        } catch (Exception exception) {
//            return ResponseEntity.status(400).body(BaseResponseBody.of(400, false,  "필수 입력 사항이 모두 입력되지 않았습니다."));
//        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, true,  "success"));
    }
}