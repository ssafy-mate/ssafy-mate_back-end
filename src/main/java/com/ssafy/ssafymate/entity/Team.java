package com.ssafy.ssafymate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
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
public class Team{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(updatable = false)
    private String campus;

    @NotNull
    @Column(updatable = false)
    private String project;

    @NotNull
    private String projectTrack;

    @NotNull
    private String teamName;

    @NotNull
    private String notice;

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
