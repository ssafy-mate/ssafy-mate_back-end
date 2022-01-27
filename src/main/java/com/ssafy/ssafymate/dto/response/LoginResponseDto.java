package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("LoginResponseDto")
@Getter
@Setter
public class LoginResponseDto{

    @ApiModelProperty(name="access-token", example = "akslndasujndoiw.asdfnjdaifnklfegsg2134.fsdfsadfsd")
    private String token;

    @ApiModelProperty(name="message", example = "로그인 하였습니다.")
    private String message;

    private Long userId;

    private String userEmail;

    private String userName;

    private String campus;

    private String ssafyTrack;

    public static LoginResponseDto of(String message, User user, String token) {
        LoginResponseDto body = new LoginResponseDto();
        body.setMessage(message);
        body.setUserId(user.getId());
        body.setUserEmail(user.getEmail());
        body.setUserName(user.getStudentName());
        body.setCampus(user.getCampus());
        body.setSsafyTrack(user.getSsafyTrack());
        body.setToken(token);
        return body;
    }
}
