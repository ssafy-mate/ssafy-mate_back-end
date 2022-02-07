package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.entity.UserTeam;

public interface UserTeamService {

    UserTeam userTeamSave(User user, Team team);

    Boolean isRecruit(Long teamId);
}
