package com.ssafy.ssafymate.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @Column(length = 600)
    private String content;

    @NotNull
    @Column(length = 50)
    private String sentTime;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "room_id")
    private ChattingRoom chattingRoom;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User user;

}
