package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.dto.TeamDto.TeamInt;
import com.ssafy.ssafymate.dto.request.TeamListRequestDto;
import com.ssafy.ssafymate.dto.request.TeamRequestDto;
import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TeamService {

    // 팀 상세 조회
    Team teamFind(Long teamId);

    // 팀 생성
    Team teamSave(TeamRequestDto teamRequestDto, MultipartFile multipartFile, User user) throws IOException;

    // 팀 수정
    Team teamModify(TeamRequestDto teamRequestDto, MultipartFile multipartFile, User user, Team team) throws IOException;

    // 팀 삭제
    void teamDelete(Long team_id);

    // 팀 참여 여부 조회
    Team belongToTeam(String selectedProject,Long userId);

    // 팀장 여부 조회
    Team ownTeam(Long teamId, Long userId);

    // 팀 리스트 검색
    Page<TeamInt> teamSearch(Pageable pageable, TeamListRequestDto teamListRequestDto, int front, int back, int total);

    // 팀 리스트 변환
    List<Team> teamListTransfer(List<TeamInt> teamIs);

}
