package com.ssafy.ssafymate.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChattingRoom {

    @Id
    @Column(name = "room_id", length = 30)
    private String roomId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id_small", referencedColumnName = "id")
    private User userId1;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id_big", referencedColumnName = "id")
    private User userId2;

    @NotNull
    @OneToMany(mappedBy = "chattingRoom")
    List<ChattingHistory> chattingHistory = new ArrayList<>();

}
