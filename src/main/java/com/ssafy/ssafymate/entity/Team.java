package com.ssafy.ssafymate.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
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

    @NotNull
    @ManyToOne
    @JoinColumn(name = "owner_id")
    User user;

    @OneToMany(mappedBy = "team")
    List<UserTeam> userTeam = new ArrayList<>();

    private String teamImg;

    @NotNull
    private int totalRecruitment;

    @NotNull
    private int frontendRecruitment;

    @NotNull
    private int backendRecruitment;

    @Column
    @CreationTimestamp
    private LocalDateTime createDateTime;
}