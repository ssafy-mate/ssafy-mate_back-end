package com.ssafy.ssafymate.dto.RequestMessageDto;

import com.ssafy.ssafymate.entity.RequestMessage;
import com.ssafy.ssafymate.entity.Team;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Setter
@Getter
public class RequestTeamMessageDto {
    Long requestId;
    Long teamId;
    String teamImgUrl;
    String teamName;
    String campus;
    String requestStatus;
    String message;
    LocalDateTime createdTime;

    public static RequestTeamMessageDto of(RequestMessage requestMessages){
        RequestTeamMessageDto res = new RequestTeamMessageDto();
        Team team = requestMessages.getTeam();
        res.setRequestId(requestMessages.getId());
        res.setTeamId(team.getId());
        res.setTeamImgUrl(team.getTeamImg());
        res.setTeamName(team.getTeamName());
        res.setCampus(team.getCampus());
        res.setRequestStatus(requestMessages.getRequestStatus());
        res.setMessage(requestMessages.getMessage());
        res.setCreatedTime(requestMessages.getCreatedDateTime().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime());
        return res;
    }
}
