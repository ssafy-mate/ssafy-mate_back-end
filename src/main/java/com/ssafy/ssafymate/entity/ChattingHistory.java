package com.ssafy.ssafymate.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChattingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String content;

    private LocalDateTime sentTime;

    @ManyToOne
    @JoinColumn(name = "chatting_room_id",referencedColumnName = "room_id")
    private ChattingRoom chattingRoom;

    @ManyToOne
    @JoinColumn(name = "sender_id",referencedColumnName = "id")
    private User user;
}