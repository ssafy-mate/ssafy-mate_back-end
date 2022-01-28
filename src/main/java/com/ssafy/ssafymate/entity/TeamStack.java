package com.ssafy.ssafymate.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

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

    @JsonBackReference
    @Column(name = "tech_stack_code")
    private Long techStackCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tech_stack_code", insertable = false, updatable = false)
    TechStack techStack;



    @JsonIgnore
    @ManyToOne
    private Team team;

}
