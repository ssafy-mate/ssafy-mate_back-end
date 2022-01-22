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
    @ManyToOne
    @JoinColumn(name = "user_id1", referencedColumnName = "id")
    private User userId1;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id2", referencedColumnName = "id")
    private User userId2;

    @NotNull
    @OneToMany(mappedBy = "chattingRoom")
    List<ChattingHistory> chattingHistory = new ArrayList<>();
}
