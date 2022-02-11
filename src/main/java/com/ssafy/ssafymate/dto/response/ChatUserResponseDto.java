package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.dto.UserDto.UserProjectLoginDto;
import com.ssafy.ssafymate.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatUserResponseDto {

    private Long userId;

    private String userName;

    private String userEmail;

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
