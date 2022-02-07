package com.ssafy.ssafymate.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ssafy.ssafymate.dto.UserDto.UserBoardInterface;
import com.ssafy.ssafymate.dto.UserDto.UserBoardDto;
import com.ssafy.ssafymate.dto.UserDto.UserListStackDto;
import com.ssafy.ssafymate.dto.request.*;
import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.entity.UserStack;
import com.ssafy.ssafymate.repository.UserRepository;
import com.ssafy.ssafymate.repository.UserStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserStackRepository userStackRepository;

    private String domainPrefix = "http://i6a402.p.ssafy.io:8080/resources/upload/";
    private String defaultImg = "default_img.jpg";

    @Override
    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return user;
    }

    @Transactional
    @Override
    public User userSave(UserRequestDto userRequestDto, MultipartFile multipartFile) throws IOException{
        String profileImgUrl;
        if (multipartFile == null || multipartFile.isEmpty()) {
            profileImgUrl = domainPrefix + defaultImg;
        } else {
            String profileImgSaveUrl = "/var/webapps/upload/" + userRequestDto.getStudentNumber() + "_" + multipartFile.getOriginalFilename();
            File file = new File(profileImgSaveUrl);
            multipartFile.transferTo(file);
            profileImgUrl = domainPrefix + userRequestDto.getStudentNumber() + "_" + multipartFile.getOriginalFilename();
        }

        String jsonString = userRequestDto.getTechStacks();
        List<UserStack> techStacks = StringToTechStacks(jsonString);

        User user = User.builder()
                .campus(userRequestDto.getCampus())
                .ssafyTrack(userRequestDto.getSsafyTrack())
                .studentNumber(userRequestDto.getStudentNumber())
                .studentName(userRequestDto.getUserName())
                .email(userRequestDto.getUserEmail())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .profileImg(profileImgUrl)
                .selfIntroduction(userRequestDto.getSelfIntroduction())
                .job1(userRequestDto.getJob1())
                .job2(userRequestDto.getJob2())
                .techStacks(techStacks)
                .githubUrl(userRequestDto.getGithubUrl())
                .etcUrl(userRequestDto.getEtcUrl())
                .agreement(userRequestDto.getAgreement())
                .roles("ROLE_USER")
                .build();
        return userRepository.save(user);
    }

    // 아이디 찾기
    @Override
    public User getUserByStudentNumberAndStudentName(String studentNumber, String studentName) {
        User user = userRepository.findByStudentNumberAndStudentName(studentNumber, studentName).orElse(null);
        return user;
    }

    // 비밀번호 재설정
    @Transactional
    @Modifying
    @Override
    public User passwordModify(PwModifyRequestDto pwModifyRequestDto, User user) {
        user.setPassword(passwordEncoder.encode(pwModifyRequestDto.getPassword()));
        return userRepository.save(user);
    }

    // 유저 수정
    @Transactional
    @Modifying
    @Override
    public User userModify(UserModifyRequestDto userModifyRequestDto, User user, String profileInfo) throws IOException {

//        if (profileInfo.equals("ssafy-track")) {
        if (userModifyRequestDto.getSsafyTrack() != null) {
            user.setSsafyTrack(userModifyRequestDto.getSsafyTrack());

        } else if (profileInfo.equals("profileImg")) {

            String profileImgUrl = user.getProfileImg();
            MultipartFile multipartFile = userModifyRequestDto.getProfileImg();
            if (multipartFile != null || !multipartFile.isEmpty()) {
                String profileImgSaveUrl = "/var/webapps/upload/" + user.getStudentNumber() + "_" + multipartFile.getOriginalFilename();
                File file = new File(profileImgSaveUrl);
                multipartFile.transferTo(file);
                profileImgUrl = domainPrefix + user.getStudentNumber() + "_" + multipartFile.getOriginalFilename();
            }
            user.setProfileImg(profileImgUrl);

        } else if (profileInfo.equals("self-introduction")) {

            user.setSelfIntroduction(userModifyRequestDto.getSelfIntroduction());

        } else if (profileInfo.equals("jobs")) {

            user.setJob1(userModifyRequestDto.getJob1());
            user.setJob2(userModifyRequestDto.getJob2());

        } else if (profileInfo.equals("tech-stacks")) {

            Long userId = user.getId();
            List<UserStack> stackInDb = userStackRepository.findAllByUserId(userId);
            if(stackInDb.size() > 0) {
                userStackRepository.deleteByUserId(userId);
            }
            String jsonString = userModifyRequestDto.getTechStacks();
            List<UserStack> techStacks = StringToTechStacks(jsonString);
            user.setTechStacks(techStacks);

        } else if (profileInfo.equals("urls")) {

            user.setGithubUrl(userModifyRequestDto.getGithubUrl());
            user.setEtcUrl(userModifyRequestDto.getEtcUrl());

        }
        return userRepository.save(user);
    }

    @Override
    public Page<UserBoardInterface> userList(Pageable pageable, UserListRequestDto user) {

        String jsonString = user.getTech_stacks();
        List<UserStack> techStacks = new ArrayList<>();
        if(jsonString != null)
            techStacks = StringToTechStacks2(jsonString);
        if((techStacks.size() == 0)){
            UserStack notin = new UserStack();
            notin.setTechStackCode(0L);
            techStacks.add(notin);
        }
        List<Long> userStacks = techStacks.stream().map(e -> e.getTechStackCode()).collect(Collectors.toList());
        Page<UserBoardInterface> users = userRepository.findStudentListJPQL(pageable,
                user.getCampus(),
                user.getSsafy_track(),
                user.getJob1(),
                user.getUser_name(),
                user.getProject(),
                user.getProject_track(),
                userStacks,
                user.getExclusion());
        System.out.println(1);
        return users;
    }

    @Override
    public List<UserBoardDto> userBoarConvert(List<UserBoardInterface> users, String project){
        List<UserBoardDto> userBoards = new ArrayList<>();
        for (UserBoardInterface user : users){
            UserBoardDto userBoardDto = new UserBoardDto();
            userBoardDto.setUserId(user.getId());
            userBoardDto.setProfileImg(        user.getProfile_img());
            userBoardDto.setCampus(user.getCampus());
            userBoardDto.setSsafyTrack(user.getSsafy_track());
            userBoardDto.setUserName(user.getStudent_name());
            userBoardDto.setJob1(user.getJob1());
            userBoardDto.setJob2(user.getJob2());
            userBoardDto.setGithubUrl(user.getGithub_url());
            if(project.equals("공통 프로젝트"))
                userBoardDto.setProjectTrack(user.getCommon_project_track());
            else if(project.equals("특화 프로젝트"))
                userBoardDto.setProjectTrack(user.getSpecialization_project_track());
            userBoardDto.setBelongToTeam(user.getBelong_to_team());
            userBoardDto.setTechStacks(UserListStackDto.of(userStackRepository.findAllByUserId(user.getId())));
            userBoards.add(userBoardDto);

        }
        return userBoards;
    }

    @Transactional
    @Modifying
    @Override
    public String selectProjectTrack(User user, UserSelectProjectTrackRequsetDto userSelectProjectTrackRequsetDto) {
        if(userSelectProjectTrackRequsetDto.getProject().equals("공통 프로젝트")){
            userRepository.updateCommonProjectTrack(user.getId(), userSelectProjectTrackRequsetDto.getProjectTrack());
        }
        else if(userSelectProjectTrackRequsetDto.getProject().equals("특화 프로젝트")){
            userRepository.updateSpecialProjectTrack(user.getId(), userSelectProjectTrackRequsetDto.getProjectTrack());
        }
        return userSelectProjectTrackRequsetDto.getProject();
    }

    // String 형태의 techStacks를 UserStack 타입의 리스트로 변환하는 메서드
    public List<UserStack> StringToTechStacks(String jsonString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type listType = new TypeToken<ArrayList<UserStack>>(){}.getType();
        List<UserStack> techStacks = gson.fromJson(jsonString, listType);
        return techStacks;
    }

    // String 형태의 Long 를 UserStack 타입의 리스트로 변환하는 메서드
    public List<UserStack> StringToTechStacks2(String jsonString) {

        List<UserStack> techStacks = new ArrayList<>();
        UserStack techstack = new UserStack();
        techstack.setTechStackCode(Long.parseLong(jsonString));
        techStacks.add(techstack);
        return techStacks;
    }

}