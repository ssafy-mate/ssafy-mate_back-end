package com.ssafy.ssafymate.controller;

import com.ssafy.ssafymate.common.ErrorResponseBody;
import com.ssafy.ssafymate.common.MessageBody;
import com.ssafy.ssafymate.common.SuccessBody;
import com.ssafy.ssafymate.common.SuccessMessageBody;
import com.ssafy.ssafymate.dto.request.EmailRequestDto;
import com.ssafy.ssafymate.dto.request.PwModifyRequestDto;
import com.ssafy.ssafymate.dto.request.PwSearchRequestDto;
import com.ssafy.ssafymate.dto.request.UserRequestDto;
import com.ssafy.ssafymate.dto.response.EmailResponseDto;
import com.ssafy.ssafymate.entity.Student;
import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.exception.EmailCodeException;
import com.ssafy.ssafymate.service.EmailService;
import com.ssafy.ssafymate.service.StudentService;
import com.ssafy.ssafymate.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

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
    public ResponseEntity<?> studentVerify(
            @RequestParam("campus") String campus, @RequestParam("studentNumber") String studentNumber, @RequestParam("userName") String userName) throws UnsupportedEncodingException {
        Student student;
        try {
            student = studentService.getStudentByStudentNumber(studentNumber);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server Error, 교육생 인증 실패"));
        }
        if (student==null || !((campus.equals(student.getCampus())) && (studentNumber.equals(student.getStudentNumber())) && (userName.equals(student.getStudentName())))) {
            return ResponseEntity.status(401).body(ErrorResponseBody.of(401, false, "해당 교육생 정보가 없습니다."));
        }
        User user = userService.getUserByStudentNumberAndStudentName(studentNumber,userName);
        if (user!=null) {
            return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "이미 가입된 교육생 입니다."));
        }
        return ResponseEntity.status(200).body(SuccessMessageBody.of(true, "교육생 인증이 완료되었습니다."));
    }

    // 회원가입 2단계 - 이메일 인증
    @GetMapping("/sign-up/verification/email")
    @ApiOperation(value = "이메일 인증 요청 & 재요청", notes = "사용자가 입력한 이메일에 인증코드 전송")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> emailConfirm(
            @RequestParam("userEmail") String userEmail) throws Exception {
        String confirm;
        User user ;
        try {
            user = userService.getUserByEmail(userEmail);
            if (user != null) {
                return ResponseEntity.status(409).body(ErrorResponseBody.of(409, false, "이미 등록된 이메일입니다."));
            }
            confirm = emailService.sendSimpleMessage(userEmail);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server Error, 이메일 전송 실패"));
        }
        return ResponseEntity.status(200).body(SuccessMessageBody.of(true, "입력한 이메일로 인증 메일을 발송했습니다.\n 이메일에 표시된 인증코드를 입력해주세요."));
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
    public ResponseEntity<?> emailAuth(
            @RequestBody EmailRequestDto emailRequestDto) throws Exception {
        Long emailAuth;
        String userEmail = emailRequestDto.getUserEmail();
        String code = emailRequestDto.getCode();
        try {
            emailAuth = emailService.getUserIdByCode(userEmail, code);
        } catch (EmailCodeException exception) {
            return ResponseEntity.status(401).body(ErrorResponseBody.of(401, false,  "올바른 인증 코드가 아닙니다."));
        } catch (NullPointerException exception) {
            return ResponseEntity.status(403).body(ErrorResponseBody.of(403, false,  "인증코드가 만료되었습니다."));
        } catch (Exception exception){
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false,  "Internal Server Error, 인증 코드 확인 실패"));
        }
        return ResponseEntity.status(200).body(SuccessBody.of(true));
    }

    // 회원가입 3단계
    @PostMapping(name = "/")
    @ApiOperation(value = "계정 생성을 위한 프로필 작성", notes = "회원가입 1~3단계를 거쳐서 작성한 회원정보를 저장하고 회원가입 완료")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> signUp(
            @Valid UserRequestDto userRequestDto, BindingResult bindingResult) throws Exception {
        // @Valid 유효성 검사를 통과하지 못하면 500 에러 반환
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(401).body(ErrorResponseBody.of(401, false,  "계정 생성이 실패하였습니다."));
        }
        try {
            userService.userSave(userRequestDto, userRequestDto.getProfileImg());
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false,  "Internal Server Error, 계정 생성 실패"));
        }
        return ResponseEntity.status(200).body(MessageBody.of("계정 생성이 완료되었습니다."));
    }

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
            @RequestParam("studentNumber") String studentNumber, @RequestParam("userName") String userName) {
        User user;
        try {
            user = userService.getUserByStudentNumberAndStudentName(studentNumber, userName);
            if (user == null) {
                return ResponseEntity.status(401).body(ErrorResponseBody.of(401, false, "일치하는 회원 정보가 없습니다."));
            }
        } catch (Exception exception) {
            return ResponseEntity.status(401).body(ErrorResponseBody.of(401, false, "일치하는 회원 정보가 없습니다."));
        }
        return ResponseEntity.status(200).body(EmailResponseDto.of(true, "%s님의 싸피메이트 계정을 찾았습니다.", user.getStudentName(), user.getEmail()));
    }

    // 비밀번호 재설정 - 이메일 인증
    @GetMapping("/pw-search")
    @ApiOperation(value = "비밀번호 재설정 - 이메일 인증 코드 발송", notes = "이메일, 학번, 이름으로 사용자 인증 후 인증코드가 담긴 이메일 발송")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> pwSearch(
            @RequestParam("userEmail") String userEmail) throws Exception {
        String pwVerification;
        User user = userService.getUserByEmail(userEmail);
        if (user == null) {
            return ResponseEntity.status(401).body(ErrorResponseBody.of(401, false, "가입 시 입력하신 회원 정보가 맞는지 다시 한번 확인해 주세요."));
        }
        try {
            pwVerification = emailService.sendPwSimpleMessage(userEmail);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "이메일 전송에 실패하였습니다."));
        }
        return ResponseEntity.status(200).body(SuccessMessageBody.of(true, "입력한 이메일로 인증 메일을 발송했습니다.\n 이메일에 표시된 인증코드를 입력해주세요."));
    }

    // 비밀번호 재설정 - 이메일 인증 코드 확인
    @PostMapping("/pw-search")
    @ApiOperation(value = "비밀번호 재설정 - 이메일 인증 코드 확인", notes = "이메일로 발송한 인증 코드를 확인")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> pwSearchEmailAuth(
            @RequestBody PwSearchRequestDto pwSearchRequestDto) throws Exception {
        Long emailAuth;
        try {
            emailAuth = emailService.getUserIdByCode(pwSearchRequestDto.getUserEmail(), pwSearchRequestDto.getCode());
        } catch (EmailCodeException exception) {
            return ResponseEntity.status(401).body(ErrorResponseBody.of(401, false,  "올바른 인증 코드가 아닙니다."));
        } catch (NullPointerException exception) {
            return ResponseEntity.status(403).body(ErrorResponseBody.of(403, false,  "인증코드가 만료되었습니다."));
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false,  "Internal Server Error, 인증 코드 확인 실패"));
        }
        return ResponseEntity.status(200).body(SuccessBody.of(true));
    }

    // 비밀번호 재설정
    @PutMapping("/pw-search")
    @ApiOperation(value = "비밀번호 재설정", notes = "인증절차를 거친 후 사용자가 비밀번호를 재설정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> modifyPassword(
            @RequestBody @Valid PwModifyRequestDto pwModifyRequestDto, BindingResult bindingResult) {
        // @Valid 유효성 검사를 통과하지 못하면 500 에러 반환
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false,  "Internal Server Error, 비밀번호 재설정 실패"));
        }
        User user = userService.getUserByEmail(pwModifyRequestDto.getUserEmail());
        try {
            userService.passwordModify(pwModifyRequestDto, user);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false,  "Internal Server, 비밀번호 재설정 실패"));
        }
        return ResponseEntity.status(200).body(SuccessMessageBody.of(true,  "비밀번호 재설정이 완료되었습니다."));
    }
}