package com.ssafy.ssafymate.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStack {

    @Id
    @JsonBackReference
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @Column(name = "tech_stack_code")
    private Long techStackCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tech_stack_code", insertable = false, updatable = false)
    TechStack techStack;

    private String techStackLevel;

    @JsonIgnore
    @ManyToOne
    private User user;
}