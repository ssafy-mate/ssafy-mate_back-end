package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.dto.request.TeamRequestDto;
import com.ssafy.ssafymate.entity.*;
import com.ssafy.ssafymate.repository.TeamRepository;
import com.ssafy.ssafymate.repository.TeamStackRepository;
import com.ssafy.ssafymate.repository.UserRepository;
import com.ssafy.ssafymate.repository.UserTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service("teamService")
public class TeamServiceImpl implements TeamService{

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamStackRepository teamStackRepository;


    // 팀 생성
    @Transactional
    @Override
    public Team teamSave(TeamRequestDto teamRequestDto, MultipartFile multipartFile, User user) throws IOException {
        String teamImgUrl;
        if(multipartFile==null || multipartFile.isEmpty()) {
            // 기본 이미지 경로 설정
            teamImgUrl = "C:\\image\\team_image\\default_img.jpg";
        } else {
            teamImgUrl = "C:\\image\\team_image\\"+ teamRequestDto.getTeamName()+"_"+ multipartFile.getOriginalFilename();

            File file = new File(teamImgUrl);
            multipartFile.transferTo(file);
        }
        System.out.println(teamImgUrl);

        Team team = teamBuilder(teamRequestDto,teamImgUrl,user);
        System.out.println(team.toString());



        return teamRepository.save(team);
    }


    //팀 수정
    @Transactional
    @Modifying
    @Override
    public Team teamModify(TeamRequestDto teamRequestDto, MultipartFile multipartFile, User user, Long teamId) throws IOException {

        // 기존 팀 스택 삭제
        List<TeamStack> stackInDb = teamStackRepository.findByTeamId(teamId);
        if(stackInDb.size() > 0){
            teamStackRepository.deleteByTeamId(teamId);
        }

        String teamImgUrl;

        if(multipartFile==null || multipartFile.isEmpty()) {
            // 기본 이미지 경로 설정
            teamImgUrl = "C:\\image\\team_image\\default_img.jpg";
        } else {
            teamImgUrl = "C:\\image\\team_image\\"+ teamRequestDto.getTeamName()+"_"+ multipartFile.getOriginalFilename();

            File file = new File(teamImgUrl);
            multipartFile.transferTo(file);
        }


        Team team = teamBuilder(teamRequestDto,teamImgUrl,user);
        team.setId(teamId);


        return teamRepository.save(team);
    }

    @Override
    public void teamDelete(Long team_id) {
        teamRepository.deleteById(team_id);
        return ;
    }


    // teamrequestDto 와 이미지Url을 team 으로 빌드
    public Team teamBuilder(TeamRequestDto teamRequestDto, String teamImgUrl, User user){
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
                .user(user)
                .build();
        return team;
    }
}
