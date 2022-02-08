package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.dto.RequestMessageDto.RequestTeamMessageDto;
import com.ssafy.ssafymate.dto.RequestMessageDto.RequestUserMessageDto;
import com.ssafy.ssafymate.entity.RequestMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class RequestMessageListResponseDto {

    List<RequestTeamMessageDto> teamRequests = new ArrayList<>();
    List<RequestUserMessageDto> userRequests = new ArrayList<>();

    public static RequestMessageListResponseDto of(List<RequestMessage> requestMessages){
        RequestMessageListResponseDto res = new RequestMessageListResponseDto();
        for (RequestMessage requestMessage : requestMessages){
            if(requestMessage.getType().equals("userRequest")){
                res.userRequests.add(RequestUserMessageDto.of(requestMessage));
            }
            else if(requestMessage.getType().equals("teamRequest")){
                res.teamRequests.add(RequestTeamMessageDto.of(requestMessage));
            }
        }
        return res;
    }
}
