package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.dto.UserDto.UserProjectDto;
import com.ssafy.ssafymate.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel("LoginResponseDto")
@Getter
@Setter
public class LoginResponseDto{

    @ApiModelProperty(name="access-token", example = "akslndasujndoiw.asdfnjdaifnklfegsg2134.fsdfsadfsd")
    private String token;

    @ApiModelProperty(name="message", example = "로그인 하였습니다.")
    private String message;

    @ApiModelProperty(name="user-id", example = "1")
    private Long userId;

    @ApiModelProperty(name="user-email", example = "ssafymate@gmail.com")
    private String userEmail;

    @ApiModelProperty(name="user-name", example = "김싸피")
    private String userName;

    @ApiModelProperty(name="student-number", example = "0648223")
    private String studentNumber;

    @ApiModelProperty(name="campus", example = "서울")
    private String campus;

    @ApiModelProperty(name="ssafy-track", example = "Java Track")
    private String ssafyTrack;

    @ApiModelProperty(name="projects")
    List<UserProjectDto> projects = new ArrayList<>();

    public static LoginResponseDto of(String message, User user, String token) {
        LoginResponseDto body = new LoginResponseDto();
        body.setMessage(message);
        body.setUserId(user.getId());
        body.setUserEmail(user.getEmail());
        body.setUserName(user.getStudentName());
        body.setStudentNumber(user.getStudentNumber());
        body.setCampus(user.getCampus());
        body.setSsafyTrack(user.getSsafyTrack());
        body.setProjects(UserProjectDto.of(user.getTeams(),user));
        body.setToken(token);
        return body;
    }
}
