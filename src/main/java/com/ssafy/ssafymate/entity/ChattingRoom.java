package com.ssafy.ssafymate.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChattingRoom {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Id
    @Column(name = "room_id")
    private String roomId;

    @NotNull
    @OneToMany(mappedBy = "chattingRoom")
    List<ChattingHistory> chattingHistory = new ArrayList<>();
}
