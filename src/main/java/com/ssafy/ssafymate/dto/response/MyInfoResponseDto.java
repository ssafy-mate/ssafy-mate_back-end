package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.dto.UserDto.UserProjectLoginDto;
import com.ssafy.ssafymate.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MyInfoResponseDto {

    @ApiModelProperty(name="user-id", example = "1")
    private Long userId;

    @ApiModelProperty(name="user-name", example = "김싸피")
    private String userName;

    @ApiModelProperty(name="user-email", example = "ssafymate@gmail.com")
    private String userEmail;

    @ApiModelProperty(name="student-number", example = "0648223")
    private String studentNumber;

    @ApiModelProperty(name="campus", example = "서울")
    private String campus;

    @ApiModelProperty(name="ssafy-track", example = "Java Track")
    private String ssafyTrack;

    @ApiModelProperty(name="projects")
    List<UserProjectLoginDto> projects = new ArrayList<>();

    public static MyInfoResponseDto of(User user) {
        MyInfoResponseDto body = new MyInfoResponseDto();
        body.setUserId(user.getId());
        body.setUserName(user.getStudentName());
        body.setUserEmail(user.getEmail());
        body.setStudentNumber(user.getStudentNumber());
        body.setCampus(user.getCampus());
        body.setSsafyTrack(user.getSsafyTrack());
        body.setProjects(UserProjectLoginDto.of(user.getTeams(),user));
        return body;
    }
}
