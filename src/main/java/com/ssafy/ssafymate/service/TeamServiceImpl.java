package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.dto.request.TeamRequestDto;
import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.repository.TeamRepository;
import com.ssafy.ssafymate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service("teamService")
public class TeamServiceImpl implements TeamService{

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Team teamSave(TeamRequestDto teamRequestDto, MultipartFile multipartFile) throws IOException {
        String teamImgUrl;
        if(multipartFile.isEmpty()) {
            // 기본 이미지 경로 설정
            teamImgUrl = "C:\\image\\team_image\\default_img.jpg";
        } else {
            teamImgUrl = "C:\\image\\team_image\\"+ teamRequestDto.getTeamName()+"_"+ multipartFile.getOriginalFilename();

            File file = new File(teamImgUrl);
            multipartFile.transferTo(file);
        }


        Team team = Team.builder()
                .campus(teamRequestDto.getCampus())
                .project(teamRequestDto.getProject())
                .projectTrack(teamRequestDto.getProjectTrack())
                .teamName(teamRequestDto.getTeamName())
                .notice(teamRequestDto.getNotice())
                .introduction(teamRequestDto.getIntroduction())
                .techStacks(teamRequestDto.getTechStacks())
                .teamImg(teamImgUrl)
                .totalRecruitment(teamRequestDto.getTotalRecruitment())
                .frontendRecruitment(teamRequestDto.getFrontendRecruitment())
                .backendRecruitment(teamRequestDto.getBackendRecruitment())
                .build();
        return teamRepository.save(team);
    }

    @Override
    public Team getTeamByUserIdAndProject(String userEmail, String selectedProject) {
        Long userId = userRepository.findByEmail(userEmail).get().getId();

        return null;
    }
}
