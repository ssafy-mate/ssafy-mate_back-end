package com.ssafy.ssafymate.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long techStackId;

    @NotNull
    @Column(length = 30)
    private String techStackName;

    @NotNull
    @Column(length = 300)
    private String techStackImgUrl;

}
