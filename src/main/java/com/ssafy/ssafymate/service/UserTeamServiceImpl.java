package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.entity.UserTeam;
import com.ssafy.ssafymate.repository.TeamRepository;
import com.ssafy.ssafymate.repository.UserTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userTeamService")
public class UserTeamServiceImpl implements UserTeamService{

    @Autowired
    private UserTeamRepository userTeamRepository;

    @Autowired
    TeamRepository teamRepository;

    @Override
    public UserTeam userTeamFind(Long userId, Long teamId) {

        return userTeamRepository.findByUserIdAndTeamId(userId,teamId).orElse(null);

    }

    @Modifying
    @Transactional
    @Override
    public Integer userTeamDelete(Long userId, Long teamId) {

        return userTeamRepository.deleteByUserIdAndTeamId(userId,teamId);

    }

    @Override
    public String isRecruit(Long teamId) {
        return teamRepository.isRecruit(teamId);
    }

}
