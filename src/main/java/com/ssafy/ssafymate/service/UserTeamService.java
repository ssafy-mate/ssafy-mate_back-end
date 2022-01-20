package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.entity.UserTeam;

public interface UserTeamService {

    UserTeam userTamSave(User user, Team team);
}
