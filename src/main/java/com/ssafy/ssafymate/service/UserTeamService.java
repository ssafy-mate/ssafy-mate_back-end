package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.entity.UserTeam;

public interface UserTeamService {

    // 팀에  속해있는지 검색
    UserTeam userTeamFind(Long userId, Long teamId);

    // 팀 탈퇴
    Integer userTeamDelete(Long userId, Long teamId);

    // 팀 가입 가능 조회
    String isRecruit(Long teamId);

}
