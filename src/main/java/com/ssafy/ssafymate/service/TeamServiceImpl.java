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
import com.ssafy.ssafymate.repository.UserTeamRepository;
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
import java.util.stream.Collectors;

@Service("teamService")
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamStackRepository teamStackRepository;

    @Autowired
    private UserTeamRepository userTeamRepository;

    String domainPrefix = "https://i6a402.p.ssafy.io:8082/resources/upload/images/team_image/";

    @Override
    public Team teamFind(Long teamId) {
        return teamRepository.findById(teamId).orElse(null);
    }

    @Transactional
    @Override
    public Team teamSave(TeamRequestDto teamRequestDto, MultipartFile multipartFile, User user) throws IOException {

        String teamImgUrl;
        if (multipartFile == null || multipartFile.isEmpty()) {
            // 기본 이미지 경로 설정
            teamImgUrl = null;
        } else {
            String profileImgSaveUrl = "/var/webapps/upload/images/team_image/" + multipartFile.getOriginalFilename();

            File file = new File(profileImgSaveUrl);
            multipartFile.transferTo(file);
            teamImgUrl = domainPrefix + multipartFile.getOriginalFilename();
        }

        Team team = teamRepository.save(teamBuilder(teamRequestDto, teamImgUrl, user));

        UserTeam userTeam = UserTeam.builder()
                .userId(user.getId())
                .teamId(team.getId())
                .build();
        userTeamRepository.save(userTeam);

        return team;

    }

    @Transactional
    @Modifying
    @Override
    public Team teamModify(TeamRequestDto teamRequestDto, MultipartFile multipartFile, User user, Team team) throws IOException {

        Long teamId = team.getId();
        // 기존 팀 스택 삭제
        List<TeamStack> stackInDb = teamStackRepository.findByTeamId(teamId);
        if (stackInDb.size() > 0) {
            teamStackRepository.deleteByTeamId(teamId);
        }

        String teamImgUrl;

        if(team.getTeamImg() != null && teamRequestDto.getTeamImgUrl() == null){
            File old_file = new File("/var/webapps/upload/images/team_image/"+getFileNameFromURL(team.getTeamImg()));
            if(old_file.exists()){
                old_file.delete();
            }
        }
        //이미지가 비어있으면 기존 이미지로
        if (multipartFile == null || multipartFile.isEmpty()) {

            teamImgUrl = teamRequestDto.getTeamImgUrl();

        } else {    // 아니면 새 이미지로
            String profileImgSaveUrl = "/var/webapps/upload/images/team_image/" + multipartFile.getOriginalFilename();

            File file = new File(profileImgSaveUrl);
            multipartFile.transferTo(file);
            teamImgUrl = domainPrefix + multipartFile.getOriginalFilename();
        }

        Team modify_team = teamBuilder(teamRequestDto, teamImgUrl, user);
        modify_team.setId(teamId);

        return teamRepository.save(modify_team);

    }

    @Override
    public void teamDelete(Long team_id) {

        Team team = teamRepository.getById(team_id);
        if(team.getTeamImg() != null){
            File old_file = new File("/var/webapps/upload/images/team_image/"+getFileNameFromURL(team.getTeamImg()));
            if(old_file.exists()){
                old_file.delete();
            }
        }

        teamRepository.deleteById(team_id);

    }

    @Override
    public Team belongToTeam(String selectedProject, Long userId) {

        return teamRepository.belongToTeam(selectedProject, userId).orElse(null);

    }

    @Override
    public Team ownTeam(Long teamId, Long userId) {

        return teamRepository.findByTeamIdAndUserIdJQL(teamId, userId).orElse(null);

    }

    @Override
    public Page<TeamInt> teamSearch(Pageable pageable, TeamListRequestDto teamListRequestDto, int front, int back, int total) {

        String jsonString = teamListRequestDto.getTechstack_id();
        List<TeamStack> techStacks = new ArrayList<>();

        if (jsonString != null)
            techStacks = StringToTechStacks2(jsonString);
        if ((techStacks.size() == 0)) {
            TeamStack notin = new TeamStack();
            notin.setTechStackCode(0L);
            techStacks.add(notin);
        }

        List<Long> teamstacks = techStacks.stream().map(TeamStack::getTechStackCode).collect(Collectors.toList());
        return teamRepository.findALLByTeamStackJQL(pageable,
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
    public List<Team> teamListTransfer(List<TeamInt> teamIs) {
        List<Team> teams = new ArrayList<>();

        for (TeamInt teamI : teamIs) {
            teams.add(teamTransfer(teamI));
        }
        return teams;

    }

    public Team teamTransfer(TeamInt teamI) {

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
    public Team teamBuilder(TeamRequestDto teamRequestDto, String teamImgUrl, User user) {

        String jsonString = teamRequestDto.getTechStacks();
        List<TeamStack> techStacks = new ArrayList<>();

        if (jsonString != null)
            techStacks = StringToTechStacks(jsonString);

        return Team.builder()
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

    }

    // String 형태의 리스트 Long TeamStack 타입의 리스트로 변환하는 메서드
    public List<TeamStack> StringToTechStacks(String jsonString) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type listType = new TypeToken<ArrayList<Long>>() {
        }.getType();
        List<Long> techStackCodes = gson.fromJson(jsonString, listType);
        return techStackCodes.stream().map(TeamStack::new).collect(Collectors.toList());

    }

    // String 형태의 Long 를 TeamStack 타입의 리스트로 변환하는 메서드
    public List<TeamStack> StringToTechStacks2(String jsonString) {

        List<TeamStack> techStacks = new ArrayList<>();
        TeamStack techstack = new TeamStack();
        techstack.setTechStackCode(Long.parseLong(jsonString));
        techStacks.add(techstack);
        return techStacks;

    }

    // url 에서 파일 이름 추출
    public static String getFileNameFromURL(String url) {

        return url.substring(url.lastIndexOf('/') + 1, url.length());

    }

}
