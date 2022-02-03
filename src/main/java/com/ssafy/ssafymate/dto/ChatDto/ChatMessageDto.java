package com.ssafy.ssafymate.dto.ChatDto;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.time.LocalDateTime;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "채팅 메세지 입력 정보", description = "타입, 방 번호, 보낸 사람 아이디, 이름, 대화 내용, 보낸 시간 등을 입력하는 클래스")
public class ChatMessageDto {

    private String roomId;

    private Long senderId;

    private String content;

    private String sentTime;

}
