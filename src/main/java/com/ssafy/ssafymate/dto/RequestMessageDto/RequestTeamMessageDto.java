package com.ssafy.ssafymate.dto.RequestMessageDto;

import com.ssafy.ssafymate.entity.RequestMessage;
import com.ssafy.ssafymate.entity.Team;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class RequestTeamMessageDto {
    Long id;
    Long teamId;
    String teamImgUrl;
    String notice;
    String teamName;
    String campus;
    String readCheck;
    String message;
    LocalDateTime creatTime;

    public static RequestTeamMessageDto of(RequestMessage requestMessages){
        RequestTeamMessageDto res = new RequestTeamMessageDto();
        Team team = requestMessages.getTeam();
        res.setId(requestMessages.getId());
        res.setTeamId(team.getId());
        res.setTeamImgUrl(team.getTeamImg());
        res.setNotice(team.getNotice());
        res.setTeamName(team.getTeamName());
        res.setCampus(team.getCampus());
        res.setReadCheck(requestMessages.getReadCheck());
        res.setMessage(requestMessages.getMessage());
        res.setCreatTime(requestMessages.getCreateDateTime());
        return res;
    }
}
