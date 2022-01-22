package com.ssafy.ssafymate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
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
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
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
    private Integer totalRecruitment;

    @NotNull
    private Integer frontendRecruitment;

    @NotNull
    private Integer backendRecruitment;

    @Column(nullable = true)
    Integer totalHeadcount;

    @Column(nullable = true)
    Integer frontendHeadcount;

    @Column(nullable = true)
    Integer backendHeadcount;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createDateTime;
}
