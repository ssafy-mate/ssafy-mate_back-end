package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.dto.RequestMessageDto.RequestMessageDto;
import com.ssafy.ssafymate.entity.RequestMessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class RequestMessageListResponseDto {

    @ApiModelProperty(value = "요청 메세지", example = "[]")
    List<RequestMessageDto> requests = new ArrayList<>();

    public static RequestMessageListResponseDto of(List<RequestMessage> requestMessages, String type) {

        RequestMessageListResponseDto res = new RequestMessageListResponseDto();

        for (RequestMessage requestMessage : requestMessages) {
            if ((type.equals("receiver") && requestMessage.getType().equals("userRequest") && requestMessage.getReceiverRead()) || (type.equals("sender") && requestMessage.getType().equals("teamRequest") && requestMessage.getSenderRead())) {
                res.requests.add(RequestMessageDto.of(requestMessage,type,"user"));
            } else if ((type.equals("receiver") && requestMessage.getType().equals("teamRequest") && requestMessage.getReceiverRead()) || (type.equals("sender") && requestMessage.getType().equals("userRequest") && requestMessage.getSenderRead())) {
                res.requests.add(RequestMessageDto.of(requestMessage,type,"team"));
            }
        }
        return res;

    }

}
