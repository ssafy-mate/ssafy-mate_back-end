package com.ssafy.ssafymate.dto.ChatDto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {

    private MessageType type;

    private String roomId;

    private Long senderId;

    private String content;

    private LocalDateTime sentTime;

}
