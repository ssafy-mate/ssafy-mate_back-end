package com.ssafy.ssafymate.dto.request;

import com.ssafy.ssafymate.entity.TeamStack;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeamRequestDto {

    private Long Id;

    private String campus;

    private String project;

    private String projectTrack;

    private String teamName;

    private String notice;

    private String introduction;

    List<TeamStack> techStacks = new ArrayList<>();

    private int totalRecruitment;

    private int frontendRecruitment;

    private int backendRecruitment;
}
