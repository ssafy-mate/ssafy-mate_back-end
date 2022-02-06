package com.ssafy.ssafymate.dto.RequestMessageDto;

import com.ssafy.ssafymate.entity.RequestMessage;
import com.ssafy.ssafymate.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUserMessageDto {
    Long id;
    Long userId;
    String profileImgUrl;
    String userName;
    String campus;
    String ssafyTrack;
    String job1;
    String readCheck;
    String message;

    public static RequestUserMessageDto of(RequestMessage requestMessages) {
        RequestUserMessageDto res = new RequestUserMessageDto();
        User user = requestMessages.getSender();
        res.setId(requestMessages.getId());
        res.setUserId(user.getId());
        res.setProfileImgUrl(user.getProfileImg());
        res.setUserName(user.getStudentName());
        res.setCampus(user.getCampus());
        res.setSsafyTrack(user.getSsafyTrack());
        res.setJob1(user.getJob1());
        res.setReadCheck(requestMessages.getReadCheck());
        res.setMessage(requestMessages.getMessage());
        return res;
    }
}
