package com.ssafy.ssafymate.dto.RequestMessageDto;

import com.ssafy.ssafymate.entity.RequestMessage;
import com.ssafy.ssafymate.entity.Team;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class RequestTeamMessageDto {
    Long requestId;
    Long teamId;
    String teamImgUrl;
    String notice;
    String teamName;
    String campus;
    String requestStatus;
    String message;
    LocalDateTime creatTime;

    public static RequestTeamMessageDto of(RequestMessage requestMessages){
        RequestTeamMessageDto res = new RequestTeamMessageDto();
        Team team = requestMessages.getTeam();
        res.setRequestId(requestMessages.getId());
        res.setTeamId(team.getId());
        res.setTeamImgUrl(team.getTeamImg());
        res.setNotice(team.getNotice());
        res.setTeamName(team.getTeamName());
        res.setCampus(team.getCampus());
        res.setRequestStatus(requestMessages.getRequestStatus());
        res.setMessage(requestMessages.getMessage());
        res.setCreatTime(requestMessages.getCreateDateTime());
        return res;
    }
}
