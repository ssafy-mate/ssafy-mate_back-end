package com.ssafy.ssafymate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeamRequestMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @NotNull
    private String type;

    @NotNull
    private Boolean readCheck;

    @NotNull
    @ManyToOne
    User sender;

    @NotNull
    @ManyToOne
    User reciver;

    @NotNull
    @ManyToOne
    Team team;
}
