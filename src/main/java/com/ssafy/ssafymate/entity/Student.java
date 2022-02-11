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
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 20)
    private String campus;

    @NotNull
    @Column(name="student_number",length = 20)
    private String studentNumber;

    @NotNull
    @Column(name="student_name", length = 20)
    private String studentName;

}
