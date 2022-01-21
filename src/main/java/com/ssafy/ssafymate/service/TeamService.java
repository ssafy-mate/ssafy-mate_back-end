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
    Optional<Team> teamfind(Long teamId);
    Team teamSave(TeamRequestDto teamRequestDto, MultipartFile multipartFile, User user) throws IOException;
    Team teamModify(TeamRequestDto teamRequestDto, MultipartFile multipartFile, User user, Long teamId) throws IOException;
    void teamDelete(Long team_id);

    Optional<Team> belongToTeam(String selecteProject,Long userId);

    Optional<Team> ownTeam(Long teamId, Long userId);

    Optional<List<Team>> teamSearch(String project, String projectTrack, List<String> teamStacks);
    Optional<List<Team>> teamSearch(String project, String projectTrack);

}
