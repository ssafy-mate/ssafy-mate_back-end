package com.ssafy.ssafymate.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ssafy.ssafymate.dto.TeamDto.TeamInt;
import com.ssafy.ssafymate.dto.request.TeamListRequestDto;
import com.ssafy.ssafymate.dto.request.TeamRequestDto;
import com.ssafy.ssafymate.entity.*;
import com.ssafy.ssafymate.repository.TeamRepository;
import com.ssafy.ssafymate.repository.TeamStackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("teamService")
public class TeamServiceImpl implements TeamService{

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamStackRepository teamStackRepository;


    @Override
    public Optional<Team> teamfind(Long teamId) {
        return teamRepository.findById(teamId);
    }

    @Transactional
    @Override
    public Team teamSave(TeamRequestDto teamRequestDto, MultipartFile multipartFile, User user) throws IOException {
        String teamImgUrl;
        if(multipartFile==null || multipartFile.isEmpty()) {
            // 기본 이미지 경로 설정
            teamImgUrl = "C:\\image\\team_image\\default_img.jpg";
        } else {
//            teamImgUrl = "C:\\image\\team_image\\"+ teamRequestDto.getTeamName()+"_"+ multipartFile.getOriginalFilename();
            teamImgUrl = "C:\\image\\team_image\\"+ teamRequestDto.getTeamName()+"_"+ multipartFile.getOriginalFilename();


            File file = new File(teamImgUrl);
            multipartFile.transferTo(file);
        }
        System.out.println(teamImgUrl);

        Team team = teamBuilder(teamRequestDto,teamImgUrl,user);
        System.out.println(team.toString());



        return teamRepository.save(team);
    }


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

    @Override
    public Optional<Team> belongToTeam(String selectedProject, Long userId) {
        return teamRepository.belongToTeam(selectedProject,userId);
    }

    @Override
    public Optional<Team> ownTeam(Long teamId, Long userId) {
        System.out.println(teamId+" "+ userId);
        return teamRepository.findByTeamIdAndUserIdJQL(teamId,userId);
    }

    @Override
    public Page<TeamInt> teamSearch(Pageable pageable, TeamListRequestDto teamListRequestDto, int front, int back, int total) {

        String jsonString = teamListRequestDto.getTechstack_code();
        List<TeamStack> techStacks = new ArrayList<>();

        if(jsonString != null)
            techStacks = StringToTechStacks2(jsonString);
        if((techStacks.size() == 0)){
            TeamStack notin = new TeamStack();
            notin.setTechStackCode(0L);
            techStacks.add(notin);
        }

        List<Long> teamstacks = techStacks.stream().map(e -> e.getTechStackCode()).collect(Collectors.toList());
        return teamRepository.findALLByteamStackJQL(pageable,
                teamListRequestDto.getCampus(),
                teamListRequestDto.getProject(),
                teamListRequestDto.getProject_track(),
                teamListRequestDto.getTeam_name(),
                front,
                back,
                total,
                teamstacks);
    }

    @Override
    public List<Team> teamListTransfer(List<TeamInt> teamIs){
        List<Team> teams = new ArrayList<>();

        for (TeamInt teamI : teamIs){
            teams.add(teamTransfer(teamI));
        }
        return teams;
    }

    public Team teamTransfer(TeamInt teamI){
        Team team = Team.builder()
                .id(teamI.getId())
                .teamImg(teamI.getTeam_img())
                .campus(teamI.getCampus())
                .project(teamI.getProject())
                .projectTrack(teamI.getProject_track())
                .notice(teamI.getNotice())
                .teamName(teamI.getTeam_name())
                .totalRecruitment(teamI.getTotal_recruitment())
                .totalHeadcount(teamI.getTotal_headcount())
                .frontendRecruitment(teamI.getFrontend_recruitment())
                .frontendHeadcount(teamI.getFrontend_headcount())
                .backendRecruitment(teamI.getBackend_recruitment())
                .backendHeadcount(teamI.getBackend_headcount())
                .createDateTime(teamI.getCreate_date_time())
                .build();

        team.setTechStacks(teamStackRepository.findByTeamId(teamI.getId()));

        return team;
    }

    // teamrequestDto 와 이미지Url을 team 으로 빌드
    public Team teamBuilder(TeamRequestDto teamRequestDto, String teamImgUrl, User user){

        String jsonString = teamRequestDto.getTechStacks();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type listType = new TypeToken<ArrayList<TeamStack>>(){}.getType();
        List<TeamStack> techStacks = gson.fromJson(jsonString, listType);

        Team team = Team.builder()
                .campus(teamRequestDto.getCampus())
                .project(teamRequestDto.getProject())
                .projectTrack(teamRequestDto.getProjectTrack())
                .teamName(teamRequestDto.getTeamName())
                .notice(teamRequestDto.getNotice())
                .introduction(teamRequestDto.getIntroduction())
                .techStacks(techStacks)
                .teamImg(teamImgUrl)
                .totalRecruitment(teamRequestDto.getTotalRecruitment())
                .frontendRecruitment(teamRequestDto.getFrontendRecruitment())
                .backendRecruitment(teamRequestDto.getBackendRecruitment())
                .owner(user)
                .build();
        return team;
    }

    // String 형태의 techStacks를 UserStack 타입의 리스트로 변환하는 메서드
    public List<TeamStack> StringToTechStacks(String jsonString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type listType = new TypeToken<ArrayList<TeamStack>>(){}.getType();
        List<TeamStack> techStacks = gson.fromJson(jsonString, listType);
        return techStacks;
    }

    // String 형태의 Long 를 UserStack 타입의 리스트로 변환하는 메서드
    public List<TeamStack> StringToTechStacks2(String jsonString) {
        List<TeamStack> techStacks = new ArrayList<>();
        TeamStack techstack = new TeamStack();
        techstack.setTechStackCode(Long.parseLong(jsonString));
        techStacks.add(techstack);
        return techStacks;
    }
}
