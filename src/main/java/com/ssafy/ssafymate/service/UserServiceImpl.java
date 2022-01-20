package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.dto.request.UserRequestDto;
import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;
    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return user;
    }

    @Override
    public User userSave(UserRequestDto userRequestDto, MultipartFile multipartFile) throws IOException {

//        File file = new File();
//        String absolutePath = new File("").getAbsolutePath() + "\\";

//        String path = ""

        String profileImgUrl;

        if(multipartFile.isEmpty()) {
            // 기본 이미지 경로 설정 - 상대경로로 바꿔야함
            profileImgUrl = "C:\\image\\default_img.jpg";
//            profileImgUrl = "\\src\\main\\resources\\static\\image\\default_img.jpg";
        } else {
            profileImgUrl = "C:\\image\\" + userRequestDto.getStudentNumber() + multipartFile.getOriginalFilename();
//            profileImgUrl = "\\src\\main\\resources\\static\\image\\" + multipartFile.getOriginalFilename();

            File file = new File(profileImgUrl);
            multipartFile.transferTo(file);
        }
        User user = User.builder()
                .campus(userRequestDto.getCampus())
                .ssafyTrack(userRequestDto.getSsafyTrack())
                .studentNumber(userRequestDto.getStudentNumber())
                .studentName(userRequestDto.getStudentName())
                .email(userRequestDto.getEmail())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .profileImg(profileImgUrl)
                .selfIntroduction(userRequestDto.getSelfIntroduction())
                .job1(userRequestDto.getJob1())
                .job2(userRequestDto.getJob2())
                .techUserStacks(userRequestDto.getTechStacks())
                .githubUrl(userRequestDto.getGithubUrl())
                .etcUrl(userRequestDto.getEtcUrl())
                .agreement(userRequestDto.getAgreement())
                .build();
        return userRepository.save(user);
    }
}
