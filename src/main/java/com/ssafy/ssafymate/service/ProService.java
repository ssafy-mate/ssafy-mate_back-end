package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.entity.Team;

import java.util.List;

public interface ProService {

    List<Team> findTeam(String campus, String project, String projectTrack);
}
