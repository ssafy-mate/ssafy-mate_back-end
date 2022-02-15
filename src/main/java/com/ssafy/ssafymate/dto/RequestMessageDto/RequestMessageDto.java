package com.ssafy.ssafymate.dto.RequestMessageDto;

import com.ssafy.ssafymate.entity.RequestMessage;
import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class RequestMessageDto {
    Long requestId;
    String originType;
    Long originId;
    String originImgUrl;
    String originName;
    String originInfo;
    String requestStatus;
    String message;
    LocalDateTime createdTime;
//    Boolean read;

    public static RequestMessageDto of(RequestMessage requestMessage, String message_type, String board_type){
        RequestMessageDto res = new RequestMessageDto();
        if(board_type.equals("user")) {
            res.setOriginType("userRequest");
            User user;
            if (message_type.equals("receiver")) {
//                res.setRead(requestMessage.getReceiverRead());
                user = requestMessage.getSender();
            } else {
//                res.setRead(requestMessage.getSenderRead());
                user = requestMessage.getReceiver();
            }
            res.setRequestId(requestMessage.getId());
            res.setOriginId(user.getId());
            res.setOriginImgUrl(user.getProfileImg());
            res.setOriginName(user.getStudentName());
            res.setOriginInfo(user.getJob1());
            res.setRequestStatus(requestMessage.getRequestStatus());
            res.setMessage(requestMessage.getMessage());
            res.setCreatedTime(requestMessage.getCreatedDateTime());
        }
        else if(board_type.equals("team")){
            res.setOriginType("teamRequest");
//            if(message_type.equals("receiver")) {
//                res.setRead(requestMessage.getReceiverRead());
//            }
//            else{
//                res.setRead(requestMessage.getSenderRead());
//            }
            Team team = requestMessage.getTeam();
            res.setRequestId(requestMessage.getId());
            res.setOriginId(team.getId());
            res.setOriginImgUrl(team.getTeamImg());
            res.setOriginName(team.getTeamName());
            res.setOriginInfo(team.getCampus());
            res.setRequestStatus(requestMessage.getRequestStatus());
            res.setMessage(requestMessage.getMessage());
            res.setCreatedTime(requestMessage.getCreatedDateTime());
        }
        return res;
    }
}
