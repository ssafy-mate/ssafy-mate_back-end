package com.ssafy.ssafymate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    User user;

    @Column(name = "user_id")
    Long userId;

    @JsonIgnore
    @NotNull
    @ManyToOne
    @JoinColumn(name = "team_id",insertable = false,updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Team team;

    @Column(name = "team_id")
    Long teamId;

}
