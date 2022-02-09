package com.ssafy.ssafymate.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
    @Column(updatable = false)
    private String campus;

    @NotNull
    private String ssafyTrack;

    @NotNull
    @Column(updatable = false)
    private String studentNumber;

    @NotNull
    @Column(updatable = false)
    private String studentName;

    @NotNull
    @Column(unique = true, updatable = false)
    private String email;

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
    List<UserStack> techStacks = new ArrayList<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    List<UserTeam> teams = new ArrayList<>();

    private String githubUrl;

    private String etcUrl;

    @NotNull
    private String agreement;

    @Column(nullable = true)
    private String commonProjectTrack;

    @Column(nullable = true)
    private String specializationProjectTrack;

    @OneToMany(mappedBy = "user")
    List<ChattingHistory> chattingHistory = new ArrayList<>();

    @Column(updatable = false)
    private String roles; // USER, ADMIN

    public List<String> getRoleList() {
        if(this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createDateTime;

    @Transient
    private Boolean belongToTeam;

}
