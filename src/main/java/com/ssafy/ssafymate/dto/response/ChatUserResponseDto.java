package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ChatUserResponseDto")
public class ChatUserResponseDto {

    @ApiModelProperty(name = "상대방의 고유 인덱스", example = "12")
    private Long userId;

    @ApiModelProperty(name = "상대방의 이름", example = "고길동")
    private String userName;

    @ApiModelProperty(name = "상대방의 이메일", example = "asd123@naver.com")
    private String userEmail;

    @ApiModelProperty(name = "상대방의 프로필 이미지", example = "https://asdasd1~")
    private String profileImgUrl;

    public static ChatUserResponseDto of(User user) {

        ChatUserResponseDto body = new ChatUserResponseDto();
        body.setUserId(user.getId());
        body.setUserName(user.getStudentName());
        body.setUserEmail(user.getEmail());
        body.setProfileImgUrl(user.getProfileImg());

        return body;

    }

}
