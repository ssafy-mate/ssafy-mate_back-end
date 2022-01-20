package com.ssafy.ssafymate.dto.request;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TeamListReuestDto {
    String project;
    String projectTrack;
    List<String> teamStacks = new ArrayList<>();

}
