package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.dto.request.TeamRequestDto;
import com.ssafy.ssafymate.entity.Team;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface TeamService {
    Team teamSave(TeamRequestDto teamRequestDto, MultipartFile multipartFile) throws IOException;

    Team getTeamByUserIdAndProject(String userEmail, String selectedProject);
}
