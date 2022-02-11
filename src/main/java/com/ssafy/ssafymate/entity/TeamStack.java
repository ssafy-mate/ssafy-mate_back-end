package com.ssafy.ssafymate.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class TeamStack {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @JsonBackReference
    @Column(name = "tech_stack_code")
    private Long techStackCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tech_stack_code", insertable = false, updatable = false)
    TechStack techStack;

    @JsonIgnore
    @ManyToOne
    private Team team;

    public TeamStack(Long techStackCode) {
        this.techStackCode = techStackCode;
    }

}
