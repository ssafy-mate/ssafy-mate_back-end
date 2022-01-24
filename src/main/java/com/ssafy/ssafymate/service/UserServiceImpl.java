package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.dto.request.PwModifyRequestDto;
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

    private PasswordEncoder passwordEncoder;

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

    @Override
    public User userSave(UserRequestDto userRequestDto, MultipartFile multipartFile) throws IOException {

        String profileImgUrl;

        if(multipartFile.isEmpty()) {
            // 기본 이미지 경로 설정 - 상대경로로 바꿔야함
            profileImgUrl = "C:\\image\\default_img.jpg";
//            profileImgUrl = "\\src\\main\\resources\\static\\image\\default_img.jpg";
        } else {
            profileImgUrl = "C:\\image\\" + userRequestDto.getStudentNumber() + "_" + multipartFile.getOriginalFilename();
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
                .techStacks(userRequestDto.getTechStacks())
                .githubUrl(userRequestDto.getGithubUrl())
                .etcUrl(userRequestDto.getEtcUrl())
                .agreement(userRequestDto.getAgreement())
                .build();
        return userRepository.save(user);
    }

    // 아이디 찾기
    @Override
    public String getEmailByStudentNumberAndStudentName(String studentNumber, String studentName) {
        User user = userRepository.findEmailByStudentNumberAndStudentName(studentNumber, studentName).orElse(null);
        String email = user.getEmail();
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
