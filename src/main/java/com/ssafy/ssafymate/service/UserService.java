package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.dto.request.UserRequestDto;
import com.ssafy.ssafymate.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    User getUserByEmail(String email);
    User userSave(UserRequestDto userRequestDto, MultipartFile multipartFile) throws IOException;
    String getEmailByStudentNumberAndStudentName(String studentNumber, String studentName);
}
