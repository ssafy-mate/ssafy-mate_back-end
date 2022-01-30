package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.dto.request.PwModifyRequestDto;
import com.ssafy.ssafymate.dto.request.UserModifyRequestDto;
import com.ssafy.ssafymate.dto.request.UserRequestDto;
import com.ssafy.ssafymate.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    // 유저 조회 - ID로 찾기
    User getUserById(Long id);

    // 유저 조회 - Email로 찾기
    User getUserByEmail(String email);

    // 유저 조회 - 학번, 이름으로 찾기
    String getEmailByStudentNumberAndStudentName(String studentNumber, String studentName);

    // 유저 비밀번호 수정
    User passwordModify(PwModifyRequestDto pwModifyRequestDto, User user);

    // 유저 생성
    User userSave(UserRequestDto userRequestDto, MultipartFile multipartFile) throws IOException;

    // 유저 수정
    User userModify(UserModifyRequestDto userModifyRequestDto, MultipartFile multipartFile, User user) throws IOException;
}
