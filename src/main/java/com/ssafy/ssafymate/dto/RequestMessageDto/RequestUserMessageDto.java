package com.ssafy.ssafymate.dto.RequestMessageDto;

import com.ssafy.ssafymate.entity.RequestMessage;
import com.ssafy.ssafymate.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RequestUserMessageDto {
    Long requestId;
    Long userId;
    String profileImgUrl;
    String userName;
    String campus;
    String ssafyTrack;
    String job1;
    String requestStatus;
    String message;
    LocalDateTime creatTime;

    public static RequestUserMessageDto of(RequestMessage requestMessages) {
        RequestUserMessageDto res = new RequestUserMessageDto();
        User user = requestMessages.getSender();
        res.setRequestId(requestMessages.getId());
        res.setUserId(user.getId());
        res.setProfileImgUrl(user.getProfileImg());
        res.setUserName(user.getStudentName());
        res.setCampus(user.getCampus());
        res.setSsafyTrack(user.getSsafyTrack());
        res.setJob1(user.getJob1());
        res.setRequestStatus(requestMessages.getRequestStatus());
        res.setMessage(requestMessages.getMessage());
        res.setCreatTime(requestMessages.getCreateDateTime());
        return res;
    }
}
