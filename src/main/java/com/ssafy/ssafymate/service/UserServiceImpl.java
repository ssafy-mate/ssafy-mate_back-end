package com.ssafy.ssafymate.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ssafy.ssafymate.dto.UserDto.UserBoardInterface;
import com.ssafy.ssafymate.dto.UserDto.UserBoardDto;
import com.ssafy.ssafymate.dto.UserDto.UserListStackDto;
import com.ssafy.ssafymate.dto.request.PwModifyRequestDto;
import com.ssafy.ssafymate.dto.request.UserListRequestDto;
import com.ssafy.ssafymate.dto.request.UserModifyRequestDto;
import com.ssafy.ssafymate.dto.request.UserRequestDto;
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
            // 기본 이미지 경로 설정 - 상대경로로 바꿔야함
        if(multipartFile == null || multipartFile.isEmpty()) {
//            profileImgUrl = "/var/webapps/upload/default_img.jpg";
            profileImgUrl = "C:\\image\\default_img.jpg";
        } else {
//            profileImgUrl = "/var/webapps/upload/" + userRequestDto.getStudentNumber() + "_" + multipartFile.getOriginalFilename();
            profileImgUrl = "C:\\image\\" + userRequestDto.getStudentNumber() + "_" + multipartFile.getOriginalFilename();

            File file = new File(profileImgUrl);
            multipartFile.transferTo(file);
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
    public String getEmailByStudentNumberAndStudentName(String studentNumber, String studentName) {
        User user = userRepository.findEmailByStudentNumberAndStudentName(studentNumber, studentName).orElse(null);
        String email = null;
        if(user != null)
            email = user.getEmail();
        return email;
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
    public User userModify(UserModifyRequestDto userModifyRequestDto, MultipartFile multipartFile, User user) throws IOException {
        // 기존 유저 스택 삭제
        Long userId = user.getId();
        List<UserStack> stackInDb = userStackRepository.findAllByUserId(userId);
        if(stackInDb.size() > 0) {
            userStackRepository.deleteByUserId(userId);
        }

        // 프로필 이미지 저장
        String modifyProfileImgUrl;
        if(multipartFile == null || multipartFile.isEmpty()) {
            modifyProfileImgUrl = "/var/webapps/upload/default_img.jpg";
//            modifyProfileImgUrl = "C:\\image\\default_img.jpg";
        } else {
            modifyProfileImgUrl = "/var/webapps/upload/" + user.getStudentNumber() + "_" + multipartFile.getOriginalFilename();
//            modifyProfileImgUrl = "C:\\image\\" + user.getStudentNumber() + "_" + multipartFile.getOriginalFilename();

            File file = new File(modifyProfileImgUrl);
            multipartFile.transferTo(file);
        }

        String jsonString = userModifyRequestDto.getTechStacks();
        List<UserStack> techStacks = StringToTechStacks(jsonString);
        
        user.setProfileImg(modifyProfileImgUrl);
        user.setSelfIntroduction(userModifyRequestDto.getSelfIntroduction());
        user.setJob1(userModifyRequestDto.getJob1());
        user.setJob2(userModifyRequestDto.getJob2());
        user.setTechStacks(techStacks);
        user.setGithubUrl(userModifyRequestDto.getGithubUrl());
        user.setEtcUrl(userModifyRequestDto.getEtcUrl());
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