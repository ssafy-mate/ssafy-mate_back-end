package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.entity.UserTeam;
import com.ssafy.ssafymate.repository.TeamRepository;
import com.ssafy.ssafymate.repository.UserTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userTeamService")
public class UserTeamServiceImpl implements UserTeamService{

    @Autowired
    private UserTeamRepository userTeamRepository;

    @Autowired
    TeamRepository teamRepository;

    @Override
    public UserTeam userTamSave(User user, Team team) {
        UserTeam userTeam = UserTeam.builder()
                .user(user)
                .team(team)
                .build();
        System.out.println(userTeam.toString());
        return userTeamRepository.save(userTeam);
    }

    @Override
    public Boolean isRecruit(Long teamId) {
        return teamRepository.isRecruit(teamId);
    }
}
