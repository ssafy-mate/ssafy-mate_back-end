package com.ssafy.ssafymate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String campus;

    @NotNull
    private String ssafyTrack;

    @NotNull
    private String studentNumber;

    @NotNull
    private String studentName;

    @NotNull
    private String email;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    private String password;

    private String profileImg;

    @NotNull
    private String selfIntroduction;

    @NotNull
    private String job1;

    private String job2;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    List<UserStack> techUserStacks = new ArrayList<>();

    private String githubUrl;

    private String etcUrl;

    @NotNull
    private Boolean agreement;

}
