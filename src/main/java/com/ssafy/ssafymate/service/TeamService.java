package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.dto.request.TeamRequestDto;
import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.TeamStack;
import com.ssafy.ssafymate.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface TeamService {
    // 팀 상세 조회
    Optional<Team> teamfind(Long teamId);

    // 팀 생성
    Team teamSave(TeamRequestDto teamRequestDto, MultipartFile multipartFile, User user) throws IOException;

    // 팀 수정
    Team teamModify(TeamRequestDto teamRequestDto, MultipartFile multipartFile, User user, Long teamId) throws IOException;

    // 팀 삭제
    void teamDelete(Long team_id);

    // 팀 참여 여부 조회
    Optional<Team> belongToTeam(String selectedProject,Long userId);

    //팀장 여부 조회
    Optional<Team> ownTeam(Long teamId, Long userId);

    // 팀 리스트 검색(전체)
    Optional<List<Team>> teamSearch(String project, String projectTrack, String teamName);

    // 팀 리스트 검색(기술 스택)
    Optional<List<Team>> teamSearch(String project, String projectTrack, String teamName, List<String> teamStacks);

}
