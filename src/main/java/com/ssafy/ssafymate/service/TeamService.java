package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.dto.request.TeamRequestDto;
import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface TeamService {
    Team teamSave(TeamRequestDto teamRequestDto, MultipartFile multipartFile, User user) throws IOException;
    Team teamModify(TeamRequestDto teamRequestDto, MultipartFile multipartFile, User user, Long teamId) throws IOException;
    void teamDelete(Long team_id);

}
