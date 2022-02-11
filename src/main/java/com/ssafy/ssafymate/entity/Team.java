package com.ssafy.ssafymate.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
public class Team{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(updatable = false, length = 20)
    private String campus;

    @NotNull
    @Column(updatable = false, length = 20)
    private String project;

    @NotNull
    @Column(length = 30)
    private String projectTrack;

    @NotNull
    @Column(length = 100)
    private String teamName;

    @NotNull
    @Column(length = 105)
    private String notice;

    @Column(length = 2022)
    private String introduction;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    List<TeamStack> techStacks = new ArrayList<>();

    @NotNull
    @ManyToOne
    @JoinColumn(name = "owner_id")
    User owner;

    @OneToMany(mappedBy = "team",fetch = FetchType.LAZY)
    List<UserTeam> members = new ArrayList<>();

    private String teamImg;

    @NotNull
    @Column(name = "total_recruitment")
    private Integer totalRecruitment;

    @NotNull
    @Column(name = "frontend_recruitment")
    private Integer frontendRecruitment;

    @NotNull
    @Column(name = "backend_recruitment")
    private Integer backendRecruitment;

    @Transient
    Integer totalHeadcount;

    @Transient
    Integer frontendHeadcount;

    @Transient
    Integer backendHeadcount;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createDateTime;

}
