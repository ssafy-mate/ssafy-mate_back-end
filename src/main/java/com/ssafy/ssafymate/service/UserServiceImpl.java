package com.ssafy.ssafymate.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ssafy.ssafymate.dto.request.PwModifyRequestDto;
import com.ssafy.ssafymate.dto.request.UserRequestDto;
import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.entity.UserStack;
import com.ssafy.ssafymate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

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

    @JsonIgnoreProperties(ignoreUnknown = true) // 1:1 매칭이 필요없는 속성은 무시함
    @Override
    public User userSave(UserRequestDto userRequestDto, MultipartFile multipartFile) throws IOException{

        String profileImgUrl;

        if(multipartFile.isEmpty()) {
            // 기본 이미지 경로 설정 - 상대경로로 바꿔야함
//            profileImgUrl = "/var/webapps/upload/default_img.jpg";
            profileImgUrl = "C:\\image\\default_img.jpg";
        } else {
//            profileImgUrl = "/var/webapps/upload/" + userRequestDto.getStudentNumber() + "_" + multipartFile.getOriginalFilename();
            profileImgUrl = "C:\\image\\" + userRequestDto.getStudentNumber() + "_" + multipartFile.getOriginalFilename();

            File file = new File(profileImgUrl);
            multipartFile.transferTo(file);
        }

        // userRequestDto 에서 String 형태의 techStacks 찾기
        String jsonString = userRequestDto.getTechStacks();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type listType = new TypeToken<ArrayList<UserStack>>(){}.getType();
        List<UserStack> techStacks = gson.fromJson(jsonString, listType);

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
    @Override
    public User passwordModify(PwModifyRequestDto pwModifyRequestDto, User user) {
//        Long userId = user.getId();
        user.setPassword(passwordEncoder.encode(pwModifyRequestDto.getPassword()));
        return userRepository.save(user);
    }
}
