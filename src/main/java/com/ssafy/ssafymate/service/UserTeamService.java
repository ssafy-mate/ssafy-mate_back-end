package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.entity.UserTeam;

public interface UserTeamService {

    UserTeam userTeamFind(Long userId, Long teamId);

    Integer userTeamDelete(Long userId, Long teamId);

    String isRecruit(Long teamId);
}
