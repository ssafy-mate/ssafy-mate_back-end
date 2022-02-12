package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.entity.Team;

import java.util.List;

public interface ProService {

    // 팀 현황 파악
    List<Team> findTeam(String campus, String project, String projectTrack);

}
