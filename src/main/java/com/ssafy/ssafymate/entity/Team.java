package com.ssafy.ssafymate.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String campus;

    @NotNull
    private String project;

    @NotNull
    private String projectTrack;

    @NotNull
    private String teamName;

    @NotNull
    private String notice;

    private String introduction;


    @NotNull
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    List<TeamStack> techStacks = new ArrayList<>();

    private String teamImg;

    @NotNull
    private int totalRecruitment;

    @NotNull
    private int frontendRecruitment;

    @NotNull
    private int backendRecruitment;
}
