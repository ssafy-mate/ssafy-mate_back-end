package com.ssafy.ssafymate.controller;

import com.ssafy.ssafymate.common.ErrorResponseBody;
import com.ssafy.ssafymate.common.MessageBody;
import com.ssafy.ssafymate.common.SuccessMessageBody;
import com.ssafy.ssafymate.dto.UserDto.UserBoardDto;
import com.ssafy.ssafymate.dto.UserDto.UserBoardInterface;
import com.ssafy.ssafymate.dto.UserDto.UserProjectLoginDto;
import com.ssafy.ssafymate.dto.request.UserListRequestDto;
import com.ssafy.ssafymate.dto.request.UserModifyRequestDto;
import com.ssafy.ssafymate.dto.request.UserSelectProjectTrackRequsetDto;
import com.ssafy.ssafymate.dto.response.*;
import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.service.TeamService;
import com.ssafy.ssafymate.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Api(value = "교육생 auth API", tags = {"UserAuth"})
@RestController
@RequestMapping("/api/auth/user")
public class UserAuthController {

    @Autowired
    UserService userService;

    @Autowired
    TeamService teamService;

    @GetMapping("/team")
    @ApiOperation(value = "팀 참여 여부 조회", notes = "유저 아이디와 선택한 프로젝트로 해당 프로젝트에서 이미 팀에 참여 했는지 여부를 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> belongToTeam(
            @RequestParam final String selectedProject,
            @AuthenticationPrincipal final String token) {
        Boolean belongToTeam = false;
        try {
            User user = userService.getUserByEmail(token);
            Long userId = user.getId();
            Team team = teamService.belongToTeam(selectedProject, userId);
            if (team == null) {
                belongToTeam = true;
            }
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server, 팀 참여 여부 조회 실패"));
        }
        return ResponseEntity.status(200).body(BelongToTeam.of(belongToTeam));
    }

    // 나의 정보 받기
    @GetMapping("/my-info")
    @ApiOperation(value = "나의 정보 조회", notes = "로그인한 유저가 토큰을 담아 요청을 보내서 유저 정보 중 일부를 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 403, message = "인증 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> getMyInfo(
            @AuthenticationPrincipal final String userEmail) {
        User user;
        try {
            user = userService.getUserByEmail(userEmail);
            if (user == null) {
                return ResponseEntity.status(403).body(ErrorResponseBody.of(403, false, "잘못된 접근입니다."));
            }
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server Error, 응답 실패"));
        }
        return ResponseEntity.status(200).body(MyInfoResponseDto.of("success", user));
    }

    // 교육생 상세 정보 조회
    @GetMapping("/info/{userId}")
    @ApiOperation(value = "교육생 상세 조회", notes = "유저 아이디로 해당 교육생 상세 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "토큰이 유효하지 않음"),
            @ApiResponse(code = 404, message = "해당 교육생 정보가 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> userDetail(
            @PathVariable final Long userId) {
        User user;
        try {
            user = userService.getUserById(userId);
            if (user == null) {
                return ResponseEntity.status(405).body(ErrorResponseBody.of(405, false, "해당 교육생 정보가 존재하지 않습니다."));
            }
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server Error, 교육생 상세 정보 조회 실패"));
        }
        return ResponseEntity.status(200).body(UserResponseDto.of(user));
    }

    // 교육생 상제 정보 수정
    @PutMapping("/info/{userId}/{profileInfo}")
    @ApiOperation(value = "교육생 상세 정보 수정", notes = "유저 아이디로 해당 교육생 상세 정보 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 403, message = "권한 없음"),
            @ApiResponse(code = 409, message = "수정 불가능"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> userModify(
            @PathVariable final Long userId,
            @PathVariable final String profileInfo,
            UserModifyRequestDto userModifyRequestDto,
            @AuthenticationPrincipal String userEmail) {
        try {
            User user = userService.getUserByEmail(userEmail);
            Long reqUserId = user.getId();
            if (!Objects.equals(reqUserId, userId)) {
                return ResponseEntity.status(400).body(ErrorResponseBody.of(400, false, "사용자는 정보를 수정할 수 있는 권한이 없습니다."));
            }
            userService.userModify(userModifyRequestDto, user, profileInfo);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server Error, 교육생 상세 정보 수정 실패"));
        }
        return ResponseEntity.status(200).body(MessageBody.of("교육생 상세 정보 수정이 완료되었습니다."));
    }

    @GetMapping("/list")
    @ApiOperation(value = "교욱생 리스트 조회", notes = "프로젝트, 프로젝트 트랙, 기술스택을 가지고 교육생 리스트 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "인증 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> searchUserList(
            @Valid UserListRequestDto userListReuestDto, BindingResult bindingResult,
            @RequestParam(required = false, defaultValue = "1", value = "nowPage") Integer nowPage
    ) {
        List<UserBoardInterface> userBoards;

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(400).body(ErrorResponseBody.of(400, false, "잘못된 입력"));
        }
        if (userListReuestDto.getCampus().equals("all") || userListReuestDto.getCampus() == null) {
            userListReuestDto.setCampus("");
        }
        if (userListReuestDto.getJob1().equals("all") || userListReuestDto.getJob1() == null) {
            userListReuestDto.setJob1("");
        }
        if (userListReuestDto.getProject_track().equals("all") || userListReuestDto.getProject_track() == null) {
            userListReuestDto.setProject_track("");
        }
        if (userListReuestDto.getSsafy_track().equals("all") || userListReuestDto.getSsafy_track() == null) {
            userListReuestDto.setSsafy_track("");
        }
        int totalPage;
        long totalElement;
        int size = 9;
        Pageable pageable = PageRequest.of(nowPage - 1, size, Sort.Direction.DESC, "id");

        if (userListReuestDto.getSort() != null) {
            if (userListReuestDto.getSort().equals("recent")) {
                pageable = PageRequest.of(nowPage - 1, size, Sort.Direction.DESC, "id");
            } else if (userListReuestDto.getSort().equals("name")) {
                pageable = PageRequest.of(nowPage - 1, size, Sort.Direction.ASC, "student_name");
            }
        }
        Page<UserBoardInterface> userPage;
        List<UserBoardDto> userBoards2;
        try {
            userPage = userService.userList(pageable, userListReuestDto);

            userBoards = userPage.getContent();
            userBoards2 = userService.userBoarConvert(userBoards, userListReuestDto.getProject());

            totalPage = userPage.getTotalPages();
            totalElement = userPage.getTotalElements();
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server Error, 교육생 리스트 조회 실패"));
        }

        return ResponseEntity.status(200).body(UserListResponseDto.of(userBoards2, userListReuestDto.getProject(), nowPage, totalPage, totalElement));
    }

    @PostMapping("/project/track")
    @ApiOperation(value = "교욱생 프로젝트 트랙 선택", notes = "프로젝트, 프로젝트 트랙을 가지고 교육생 프로젝트 트랙 선택")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "인증 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> selectProjectTrack(
            @RequestBody @Valid UserSelectProjectTrackRequsetDto userSelectProjectTrackRequsetDto,
            @AuthenticationPrincipal final String token
    ) {
        String project = userSelectProjectTrackRequsetDto.getProject();
        try {
            User user = userService.getUserByEmail(token);
            if (project.equals("공통 프로젝트")) {
                if (user.getCommonProjectTrack() != null) {
                    return ResponseEntity.status(400).body(ErrorResponseBody.of(400, false, "이미 " + project + " 트랙 선택을 완료 하였습니다."));
                }
            } else if (project.equals("특화 프로젝트")) {
                if (user.getSpecializationProjectTrack() != null) {
                    return ResponseEntity.status(400).body(ErrorResponseBody.of(400, false, "이미 " + project + " 트랙 선택을 완료 하였습니다."));
                }
            }
            userService.selectProjectTrack(user, userSelectProjectTrackRequsetDto);

        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server Error, 교육생 리스트 조회 실패"));
        }
        return ResponseEntity.status(200).body(SuccessMessageBody.of(true, project + " 트랙 선택이 완료되었습니다."));
    }

    @PutMapping("/project/track")
    @ApiOperation(value = "교욱생 프로젝트 트랙 선택", notes = "프로젝트, 프로젝트 트랙을 가지고 교육생 프로젝트 트랙 선택")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "인증 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> modifyProjectTrack(
            @RequestBody @Valid UserSelectProjectTrackRequsetDto userSelectProjectTrackRequsetDto,
            @AuthenticationPrincipal final String token) {
        String project = userSelectProjectTrackRequsetDto.getProject();
        try {
            User user = userService.getUserByEmail(token);
            Team team = teamService.belongToTeam(project, user.getId());
            if (project.equals("공통 프로젝트")) {
                if (user.getCommonProjectTrack() != null) {
                    return ResponseEntity.status(400).body(ErrorResponseBody.of(400, false, "이미 " + project + " 트랙 선택을 완료 하였습니다."));
                }
            } else if (project.equals("특화 프로젝트")) {
                if (user.getSpecializationProjectTrack() != null) {
                    return ResponseEntity.status(400).body(ErrorResponseBody.of(400, false, "이미 " + project + " 트랙 선택을 완료 하였습니다."));
                }
            }
            userService.selectProjectTrack(user, userSelectProjectTrackRequsetDto);

        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server Error, 교육생 리스트 조회 실패"));
        }
        return ResponseEntity.status(200).body(SuccessMessageBody.of(true, project + " 트랙 선택이 완료되었습니다."));
    }

    // 교육생 프로젝트 정보 받기
    @GetMapping("/projects")
    @ApiOperation(value = "교육생 프로젝트 정보 받기", notes = "교육생 프로젝트 정보 받기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = LoginResponseDto.class),
            @ApiResponse(code = 401, message = "인증 실패", response = ErrorResponseBody.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> userProjects(@AuthenticationPrincipal final String token) {

        User user;
        try {
            user = userService.getUserByEmail(token);

        } catch (Exception exception) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false, "Internal Server Error, 프로젝트 정보 갱신 실패"));
        }
        return ResponseEntity.status(200).body(UserProjectResponseDto.of(UserProjectLoginDto.of(user.getTeams(), user)));
    }

}
