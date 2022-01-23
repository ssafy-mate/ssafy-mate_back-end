package com.ssafy.ssafymate.dto.ChatDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessageDto {

    private String roomId;

    private Long senderId;

    private String content;

    private LocalDateTime sentTime;

}
